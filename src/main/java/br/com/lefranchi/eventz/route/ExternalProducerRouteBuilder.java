package br.com.lefranchi.eventz.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import br.com.lefranchi.eventz.domain.Producer;

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

		// TODO: A forma de coletar a inforação externa deve ser definida no
		// Producer. Definir o nome da fila de destino para o nome do produtor.
		from("").process(new Processor() {

			@Override
			public void process(final Exchange exchange) throws Exception {
				// TODO Auto-generated method stub

			}
		}).to("activemq:queue:" + getProducer().getName().trim());

	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(final Producer producer) {
		this.producer = producer;
	}

}
