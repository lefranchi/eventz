package br.com.lefranchi.eventz.simulator;

import java.util.Date;

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
		// bind MyBean into the registery
		main.bind("foo", new MyBean());
		// add routes
		main.addRouteBuilder(new MyRouteBuilder());
		// add event listener
		main.addMainListener(new Events());

		// run until you terminate the JVM
		System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
		main.run();

	}

	private static class MyRouteBuilder extends RouteBuilder {
		@Override
		public void configure() throws Exception {
			from("timer:foo?delay=2000").process(new Processor() {
				@Override
				public void process(final Exchange exchange) throws Exception {
					System.out.println("Invoked timer at " + new Date());
				}
			}).bean("foo");
		}
	}

	public static class MyBean {
		public void callMe() {
			System.out.println("MyBean.calleMe method has been called");
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
