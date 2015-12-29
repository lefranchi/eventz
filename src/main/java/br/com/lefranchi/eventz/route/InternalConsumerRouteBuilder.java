package br.com.lefranchi.eventz.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.domain.ProducerData;
import br.com.lefranchi.eventz.engine.RuleEngine;

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

		from("activemq:queue:" + getProducer().getName().trim()).process(new Processor() {

			@Override
			public void process(final Exchange exchange) throws Exception {

				final ProducerData data = new ProducerData();
				data.setProducer(producer);
				data.setData(exchange.getIn().getBody(String.class));

				final RuleEngine ruleEngine = new RuleEngine();

				// TODO: Resolver problemas de lazy loading.
				if (producer.getRules() != null && producer.getRules().size() > 0) {

					producer.getRules().forEach((rule) -> {
						try {
							ruleEngine.processRule(rule, data);
						} catch (final Exception e) {
							LOGGER.error(String.format("Erro na execução da regra %s do produtor %s", rule, producer),
									e);
						}
					});

				} else {
					LOGGER.warn(String.format("Recebando mensagem para o produtor %s sem regras.", producer));
				}

			}

		}).to("log:Regra processada.");

	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(final Producer producer) {
		this.producer = producer;
	}

}
