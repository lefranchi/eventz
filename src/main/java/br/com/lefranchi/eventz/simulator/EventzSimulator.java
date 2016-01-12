package br.com.lefranchi.eventz.simulator;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.main.MainListenerSupport;
import org.apache.camel.main.MainSupport;

import br.com.lefranchi.eventz.testutils.ProducerDataTestUtils;

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
					System.out.println("Invoked timer at " + new Date());
					exchange.getIn().setBody(ProducerDataTestUtils.PRODUCER_DATA, String.class);
				}
			}).to("http4://0.0.0.0:2121/eventz/B1/");
		}
	}

	public static class Events extends MainListenerSupport {

		@Override
		public void afterStart(final MainSupport main) {
			System.out.println("MainExample with Camel is now started!");
		}

		@Override
		public void beforeStop(final MainSupport main) {
			System.out.println("MainExample with Camel is now being stopped!");
		}
	}

}
