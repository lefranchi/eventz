package br.com.lefranchi.eventz.route;

import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.OnExceptionDefinition;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lefranchi.eventz.domain.EventToProcess;
import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.domain.ProducerData;
import br.com.lefranchi.eventz.engine.RuleProcessorVO;

/**
 * Rotas que consomem a fila interna e executam as regras.
 * 
 * @author lfranchi
 *
 */
public class InternalConsumerRouteBuilder extends RouteBuilder {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalConsumerRouteBuilder.class);

	/**
	 * Produtor que será coletado.
	 */
	private Producer producer;

	/**
	 * Construtor.
	 */
	public InternalConsumerRouteBuilder(final Producer producer) {
		super();
		this.setProducer(producer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {

		final String queueName = getProducer().getName().replaceAll("\\s+", "");

		errorHandler(loggingErrorHandler("internal.consumer").level(LoggingLevel.ERROR));

		if (producer.getEventsOnException() != null && producer.getEventsOnException().size() > 0) {

			final OnExceptionDefinition exceptionDefinition = onException(RuntimeException.class);

			if (producer.getEventsOnException().size() > 1)
				exceptionDefinition.multicast().parallelProcessing();

			producer.getEventsOnException().forEach((eventToProcess) -> {
				if (eventToProcess.getProperties() != null && !eventToProcess.getProperties().isEmpty()) {
					exceptionDefinition.pipeline().process(new Processor() {
						@Override
						public void process(final Exchange exchange) throws Exception {
							exchange.getIn().getHeaders().put("eventPropeties", eventToProcess.getProperties());
						}
					});
				}

				exceptionDefinition.process(eventToProcess.getEvent().getProcessor());

				if (eventToProcess.getProperties() != null && !eventToProcess.getProperties().isEmpty())
					exceptionDefinition.end();

			});

			if (producer.getEventsOnException().size() > 1)
				exceptionDefinition.end();

		}

		final RouteDefinition routeDefinition = from("activemq:" + queueName)

				.process(new Processor() {

					@Override
					public void process(final Exchange exchange) throws Exception {

						final ProducerData data = new ProducerData();
						data.setProducer(producer);
						data.setData(exchange.getIn().getBody(String.class));

						final RuleProcessorVO vo = new RuleProcessorVO();
						vo.setData(data);

						exchange.getIn().setBody(vo);

					}

				});

		if (producer.getRules() != null && producer.getRules().size() > 0) {

			if (producer.getRules().size() > 1)
				routeDefinition.multicast().parallelProcessing();

			producer.getRules().forEach((rule) -> {

				routeDefinition.process(new Processor() {
					@Override
					public void process(final Exchange exchange) throws Exception {
						exchange.getIn().getBody(RuleProcessorVO.class).setRule(rule);
					}
				});

				routeDefinition.process("ruleProcessor");

				routeDefinition.choice().when(new Predicate() {
					@Override
					public boolean matches(final Exchange exchange) {
						return exchange.getIn().getBody(RuleProcessorVO.class).getResult();
					}
				});

				processEvents(routeDefinition, rule.getEventsOnTrue());

				routeDefinition.choice().when(new Predicate() {
					@Override
					public boolean matches(final Exchange exchange) {
						return !exchange.getIn().getBody(RuleProcessorVO.class).getResult();
					}
				});

				processEvents(routeDefinition, rule.getEventsOnFalse());

				processEvents(routeDefinition, rule.getEventsOnAlways());

			});

			if (producer.getRules().size() > 1)
				routeDefinition.end();

		}

		routeDefinition.to("log:Execucao de rota finalizada.");

	}

	private void processEvents(final RouteDefinition routeDefinition, final Set<EventToProcess> eventsToProcess) {

		if (eventsToProcess == null)
			return;

		if (eventsToProcess.size() > 0) {

			if (eventsToProcess.size() > 1)
				routeDefinition.multicast().parallelProcessing();

			eventsToProcess.forEach((eventToProcess) -> {

				if (eventToProcess.getProperties() != null && !eventToProcess.getProperties().isEmpty()) {
					routeDefinition.pipeline().process(new Processor() {
						@Override
						public void process(final Exchange exchange) throws Exception {
							exchange.getIn().getHeaders().put("eventProperties", eventToProcess.getProperties());
						}
					});
				}

				routeDefinition.process(eventToProcess.getEvent().getProcessor());

				if (eventToProcess.getProperties() != null && !eventToProcess.getProperties().isEmpty())
					routeDefinition.end();

			});

			if (eventsToProcess.size() > 1)
				routeDefinition.end();

		}

	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(final Producer producer) {
		this.producer = producer;
	}

}
