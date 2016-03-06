package br.com.ablebit.eventz.route;

import org.apache.camel.builder.RouteBuilder;

import br.com.ablebit.eventz.domain.Producer;
import br.com.ablebit.eventz.domain.ProducerInputMethod;

/**
 * Rotas que recebem/coletam informações ao mundo externo e produzem objetos na
 * fila de consumo interno.
 * 
 * @author lfranchi
 *
 */
public class ExternalProducerRouteBuilder extends RouteBuilder {

	/**
	 * Produtor que será coletado.
	 */
	private Producer producer;

	/**
	 * Construtor.
	 */
	public ExternalProducerRouteBuilder(final Producer producer) {
		super();
		this.producer = producer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {

		final ProducerInputMethod producerInputMethod = extractProducerInputMethod();

		final StringBuilder fromClause = new StringBuilder(producerInputMethod.getInputMethod().getComponentName()
				.replaceAll("\\bINPUT_METHOD_COMMAND\\b", producer.getInputMethodCommand())).append("?");

		if (producerInputMethod.getProperties() != null) {
			producerInputMethod.getProperties().forEach((key, value) -> {
				fromClause.append(key).append("=").append(value).append("&");
			});
		}

		fromClause.deleteCharAt(fromClause.length() - 1);

		from(fromClause.toString()).to("activemq:queue:" + getProducer().getName().trim());

	}

	private ProducerInputMethod extractProducerInputMethod() {

		ProducerInputMethod producerInputMethod = null;

		if (producer.getInputMethod() != null) {
			producerInputMethod = producer.getInputMethod();
		} else {
			if (producer.getProducerGroup() != null) {
				if (producer.getProducerGroup().getInputMethod() != null) {
					producerInputMethod = producer.getProducerGroup().getInputMethod();
				}
			}
		}

		return producerInputMethod;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(final Producer producer) {
		this.producer = producer;
	}

}
