package br.com.ablebit.eventz.engine.event;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.ablebit.eventz.domain.ProducerData;
import br.com.ablebit.eventz.engine.RuleProcessorVO;

/**
 * Escreve dado no log.
 * 
 * @author lfranchi
 *
 */
@Component
public class EventLog implements Processor {

	/**
	 * Logger.
	 */
	final static Logger LOGGER = LoggerFactory.getLogger(EventLog.class);

	@Override
	public void process(final Exchange exchange) throws Exception {

		final ProducerData data = exchange.getIn().getBody(RuleProcessorVO.class).getData();

		LOGGER.info(String.format("Processando evento de impressao em tela para %s", data));

	}

}
