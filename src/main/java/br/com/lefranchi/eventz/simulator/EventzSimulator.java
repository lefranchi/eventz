package br.com.lefranchi.eventz.simulator;

import java.util.Date;
import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.main.MainListenerSupport;
import org.apache.camel.main.MainSupport;

/**
 * Simulador que deve gerar dados de entrada para os produtores.
 * 
 * @author lfranchi
 *
 */
public class EventzSimulator {

	private Main main;

	public static void main(final String[] args) throws Exception {
		final EventzSimulator eventzSimulator = new EventzSimulator();
		eventzSimulator.boot();
	}

	private void boot() throws Exception {
		// create a Main instance
		main = new Main();
		// enable hangup support so you can press ctrl + c to terminate the JVM
		main.enableHangupSupport();
		// add routes
		// main.addRouteBuilder(new ConsumerRouteBuilder());
		main.addRouteBuilder(new ProducerRouteBuilder());
		// add event listener
		main.addMainListener(new Events());

		// run until you terminate the JVM
		System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
		main.run();

	}

	private static class ConsumerRouteBuilder extends RouteBuilder {
		@Override
		public void configure() throws Exception {
			from("jetty:http://0.0.0.0:2121/eventz/simulator/").process(new Processor() {
				@Override
				public void process(final Exchange exchange) throws Exception {
					System.out.println("Invoked timer at " + new Date() + " and has body "
							+ exchange.getIn().getBody(String.class));
				}
			}).log("finished");
		}
	}

	private static class ProducerRouteBuilder extends RouteBuilder {
		@Override
		public void configure() throws Exception {
			from("timer:foo?delay=12000").process(new Processor() {
				@Override
				public void process(final Exchange exchange) throws Exception {

					// id;name;year;active;content
					// 88745;LEANDRO FRANCHI;1979;0;25.4
					final String dataSample = "%d;LEANDRO FRANCHI;%d;%d;%f";

					final int id = new Random().nextInt((90000 - 10000) + 1) + 10000;
					final int year = new Random().nextInt((2015 - 1000) + 1) + 1000;
					final int active = new Random().nextInt((1 - 0) + 1) + 0;
					final float content = new Random().nextFloat();

					final String data = String.format(dataSample, id, year, active, content);

					System.out.println("Invoked timer at " + new Date() + " Data=" + data);

					exchange.getIn().setBody(data, String.class);

				}
			}).to("http4://0.0.0.0:2121/eventz/B1/");
		}
	}

	public static class Events extends MainListenerSupport {

		@Override
		public void afterStart(final MainSupport main) {
			System.out.println("Eventz Simulator iniciado!");
		}

		@Override
		public void beforeStop(final MainSupport main) {
			System.out.println("Eventz Simulator Finalizado!");
		}
	}

}
