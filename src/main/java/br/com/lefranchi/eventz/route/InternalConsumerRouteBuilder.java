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
import br.com.lefranchi.eventz.domain.Rule;
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

		final Set<EventToProcess> eventsOnException = extractEventsOnException();

		if (eventsOnException != null && eventsOnException.size() > 0) {

			final OnExceptionDefinition exceptionDefinition = onException(RuntimeException.class);

			if (eventsOnException.size() > 1)
				exceptionDefinition.multicast().parallelProcessing();

			eventsOnException.forEach((eventToProcess) -> {
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

			if (eventsOnException.size() > 1)
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

		final Set<Rule> rules = extractRules();

		if (rules != null && rules.size() > 0) {

			if (rules.size() > 1)
				routeDefinition.multicast().parallelProcessing();

			rules.forEach((rule) -> {

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

			if (rules.size() > 1)
				routeDefinition.end();

		}

		final Set<EventToProcess> eventsOnAlways = extractEventsOnAwalys();

		if (eventsOnAlways != null && eventsOnAlways.size() > 0) {

			if (eventsOnAlways.size() > 1)
				routeDefinition.multicast().parallelProcessing();

			eventsOnAlways.forEach((eventToProcess) -> {
				routeDefinition.process(eventToProcess.getEvent().getProcessor());
			});

			if (eventsOnAlways.size() > 1)
				routeDefinition.end();

		}

		routeDefinition.to("log:Execucao de rota finalizada.");

	}

	private Set<Rule> extractRules() {

		Set<Rule> rules = null;

		if (producer.getRules() != null && producer.getRules().size() > 0) {
			rules = producer.getRules();
		} else {
			if (producer.getProducerGroup() != null) {
				if (producer.getProducerGroup().getRules() != null
						&& producer.getProducerGroup().getRules().size() > 0) {
					rules = producer.getProducerGroup().getRules();
				}
			}
		}

		return rules;
	}

	private Set<EventToProcess> extractEventsOnAwalys() {

		Set<EventToProcess> events = null;

		if (producer.getEventsOnAlways() != null && producer.getEventsOnAlways().size() > 0) {
			events = producer.getEventsOnAlways();
		} else {
			if (producer.getProducerGroup() != null) {
				if (producer.getProducerGroup().getEventsOnAlways() != null
						&& producer.getProducerGroup().getEventsOnAlways().size() > 0) {
					events = producer.getProducerGroup().getEventsOnAlways();
				}
			}
		}

		return events;
	}

	private Set<EventToProcess> extractEventsOnException() {

		Set<EventToProcess> events = null;

		if (producer.getEventsOnException() != null && producer.getEventsOnException().size() > 0) {
			events = producer.getEventsOnException();
		} else {
			if (producer.getProducerGroup() != null) {
				if (producer.getProducerGroup().getEventsOnException() != null
						&& producer.getProducerGroup().getEventsOnException().size() > 0) {
					events = producer.getProducerGroup().getEventsOnException();
				}
			}
		}

		return events;
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
