package br.com.lefranchi.eventz.engine.event;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.lefranchi.eventz.domain.EventProperty;

/**
 * Envia SMS.
 * 
 * @author lfranchi
 *
 */
@Component
public class EventSms implements Processor {

	/**
	 * Logger.
	 */
	final static Logger LOGGER = LoggerFactory.getLogger(EventSms.class);

	@Override
	public void process(final Exchange exchange) throws Exception {

		// final ProducerData data =
		// exchange.getIn().getBody(RuleProcessorVO.class).getData();
		// final Map<EventProperty, String> eventProperties =
		// (Map<EventProperty, String>) exchange.getIn()
		// .getHeader("eventProperties");

		LOGGER.info("Processando SMS...");

	}

	// TODO CRIAR EM UTILS
	private String getPropertyValue(final Map<EventProperty, String> eventProperties, final String name) {

		String retValue = null;

		if (eventProperties == null || eventProperties.isEmpty())
			return null;

		for (final EventProperty ep : eventProperties.keySet()) {
			if (ep.getName().equals(name)) {
				retValue = eventProperties.get(ep);
				break;
			}
		}

		return retValue;

	}

}
