package br.com.lefranchi.eventz.engine.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lefranchi.eventz.domain.ProducerData;
import br.com.lefranchi.eventz.domain.Rule;

/**
 * Escreve dado no log.
 * 
 * @author lfranchi
 *
 */
public class EventLog extends EventProcessor {

	/**
	 * Logger.
	 */
	final static Logger logger = LoggerFactory.getLogger(EventLog.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.lefranchi.events.core.processor.EventProcessor#process(br.com.
	 * lefranchi.events.core.model.ProducerData)
	 */
	@Override
	public void process(final ProducerData data, final Rule rule) {

		logger.info(String.format("Processando evento de impressao em tela para %s", data));

	}

}
