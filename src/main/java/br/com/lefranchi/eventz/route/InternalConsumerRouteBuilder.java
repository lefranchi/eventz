package br.com.lefranchi.eventz.route;

import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.MulticastDefinition;
import org.apache.camel.model.OnExceptionDefinition;
import org.apache.camel.model.PipelineDefinition;
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

			final MulticastDefinition multicastDefinition = exceptionDefinition.multicast();

			eventsOnException.forEach((eventToProcess) -> {

				final PipelineDefinition pipelineDefinition = multicastDefinition.pipeline();

				if (eventToProcess.getProperties() != null && !eventToProcess.getProperties().isEmpty()) {
					pipelineDefinition.process(new Processor() {
						@Override
						public void process(final Exchange exchange) throws Exception {
							exchange.getIn().getHeaders().put("eventPropeties", eventToProcess.getProperties());
						}
					});
				}

				pipelineDefinition.process(eventToProcess.getEvent().getProcessor());

				pipelineDefinition.end();

			});

			multicastDefinition.end();

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

		routeDefinition.setId(String.format("internal_%s", getProducer().getName().replaceAll("\\s+", "")));

		final Set<Rule> rules = extractRules();

		if (rules != null && rules.size() > 0) {

			final MulticastDefinition multicastDefinition = routeDefinition.multicast();

			rules.forEach((rule) -> {

				final PipelineDefinition pipelineDefinition = multicastDefinition.pipeline();

				pipelineDefinition.process(new Processor() {
					@Override
					public void process(final Exchange exchange) throws Exception {
						exchange.getIn().getBody(RuleProcessorVO.class).setRule(rule);
					}
				});

				pipelineDefinition.process("ruleProcessor");

				pipelineDefinition.choice().when(new Predicate() {
					@Override
					public boolean matches(final Exchange exchange) {
						return exchange.getIn().getBody(RuleProcessorVO.class).getResult();
					}
				});

				processEvents(pipelineDefinition, rule.getEventsOnTrue());

				pipelineDefinition.choice().when(new Predicate() {
					@Override
					public boolean matches(final Exchange exchange) {
						return !exchange.getIn().getBody(RuleProcessorVO.class).getResult();
					}
				});

				processEvents(pipelineDefinition, rule.getEventsOnFalse());

				processEvents(pipelineDefinition, rule.getEventsOnAlways());

				pipelineDefinition.end();

			});

			multicastDefinition.end();

		}

		final Set<EventToProcess> eventsOnAlways = extractEventsOnAwalys();

		if (eventsOnAlways != null && eventsOnAlways.size() > 0) {

			if (eventsOnAlways.size() > 1)
				routeDefinition.multicast();

			for (final EventToProcess eventToProcess : eventsOnAlways) {
				// eventsOnAlways.forEach((eventToProcess) -> {
				routeDefinition.process(eventToProcess.getEvent().getProcessor());
				// });
			}

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

	private void processEvents(final PipelineDefinition pipelineDefinition, final Set<EventToProcess> eventsToProcess) {

		if (eventsToProcess == null)
			return;

		if (eventsToProcess.size() > 0) {

			final MulticastDefinition multicastDefinition = pipelineDefinition.multicast();

			eventsToProcess.forEach((eventToProcess) -> {

				final PipelineDefinition pipelineDefinition2 = multicastDefinition.pipeline();

				if (eventToProcess.getProperties() != null && !eventToProcess.getProperties().isEmpty()) {
					pipelineDefinition2.process(new Processor() {
						@Override
						public void process(final Exchange exchange) throws Exception {
							exchange.getIn().getHeaders().put("eventProperties", eventToProcess.getProperties());
						}
					});
				}

				pipelineDefinition2.process(eventToProcess.getEvent().getProcessor());

				pipelineDefinition2.end();

			});

			multicastDefinition.end();

		}

	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(final Producer producer) {
		this.producer = producer;
	}

}
