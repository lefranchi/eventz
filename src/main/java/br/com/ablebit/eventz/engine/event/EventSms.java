package br.com.ablebit.eventz.engine.event;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.ablebit.eventz.domain.EventProperty;
import br.com.ablebit.eventz.domain.ProducerData;
import br.com.ablebit.eventz.engine.RuleProcessorVO;
import br.com.ablebit.eventz.util.EventUtils;
import br.com.ablebit.eventz.util.PositionalUtils;

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

	@SuppressWarnings("unchecked")
	@Override
	public void process(final Exchange exchange) throws Exception {

		final ProducerData data = exchange.getIn().getBody(RuleProcessorVO.class).getData();
		final Map<EventProperty, String> eventProperties = (Map<EventProperty, String>) exchange.getIn()
				.getHeader("eventProperties");

		LOGGER.info("Processando SMS...");

		final String url = EventUtils.getPropertyValue(eventProperties, "url");
		final int numeroChip = Integer.valueOf(EventUtils.getPropertyValue(eventProperties, "numeroChip"));
		final String messageFormat = EventUtils.getPropertyValue(eventProperties, "messageFormat");

		final String text = PositionalUtils.format(messageFormat, data.getDataValues());

		LOGGER.info(String.format("Enviando mensagem %s por SMS para %s", text, String.valueOf(numeroChip)));

	}

}
