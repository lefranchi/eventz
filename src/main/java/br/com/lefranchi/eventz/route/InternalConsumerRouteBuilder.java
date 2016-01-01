package br.com.lefranchi.eventz.route;

import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lefranchi.eventz.domain.Event;
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

		final RouteDefinition routeDefinition = from("activemq:queue:" + getProducer().getName().trim())

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

			routeDefinition.end();

		}

	}

	private void processEvents(final RouteDefinition routeDefinition, final Set<Event> events) {

		if (events.size() > 0) {

			routeDefinition.multicast().parallelProcessing();

			events.forEach((event) -> {
				// TODO: O NOME DO PROCESSADOR DEVE ESTAR CONFIGURADO
				routeDefinition.process(event.getProcessor());
			});

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
