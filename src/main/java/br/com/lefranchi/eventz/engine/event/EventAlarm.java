package br.com.lefranchi.eventz.engine.event;

import java.util.Calendar;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.lefranchi.eventz.domain.Alarm;
import br.com.lefranchi.eventz.domain.ProducerData;
import br.com.lefranchi.eventz.domain.Rule;
import br.com.lefranchi.eventz.engine.RuleProcessorVO;

/**
 * Cria alarme e salva no banco de dados.
 * 
 * @author lfranchi
 *
 */
@Component
public class EventAlarm implements Processor {

	// TODO: CRIAR TESTCASE

	/**
	 * Logger.
	 */
	final static Logger LOGGER = LoggerFactory.getLogger(EventAlarm.class);

	@Override
	public void process(final Exchange exchange) throws Exception {

		final ProducerData data = exchange.getIn().getBody(RuleProcessorVO.class).getData();
		final Rule rule = exchange.getIn().getBody(RuleProcessorVO.class).getRule();

		LOGGER.info(String.format("Criando alarme para %s", data));

		final Alarm alarm = new Alarm();

		alarm.setDate(Calendar.getInstance());
		alarm.setFormula(rule.getFormula());
		alarm.setProducerData(data);

		// TODO: Salvar alarme

	}

}
