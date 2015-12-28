package br.com.lefranchi.eventz.engine.event;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lefranchi.eventz.domain.Alarm;
import br.com.lefranchi.eventz.domain.ProducerData;
import br.com.lefranchi.eventz.domain.Rule;

/**
 * Cria alarme e salva no banco de dados.
 * 
 * @author lfranchi
 *
 */
public class EventAlarm extends EventProcessor {

	/**
	 * Logger.
	 */
	final static Logger logger = LoggerFactory.getLogger(EventAlarm.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.lefranchi.events.core.processor.EventProcessor#process(br.com.
	 * lefranchi.events.core.model.ProducerData)
	 */
	@Override
	public void process(final ProducerData data, final Rule rule) {

		logger.info(String.format("Criando alarme para %s", data));

		final Alarm alarm = new Alarm();

		alarm.setDate(Calendar.getInstance());
		alarm.setFormula(rule.getFormula());
		alarm.setProducerData(data);

		// TODO: Salvar alarme

	}

}
