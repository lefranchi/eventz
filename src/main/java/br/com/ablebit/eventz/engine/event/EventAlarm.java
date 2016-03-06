package br.com.ablebit.eventz.engine.event;

import java.util.Calendar;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ablebit.eventz.domain.Alarm;
import br.com.ablebit.eventz.domain.AlarmLevel;
import br.com.ablebit.eventz.domain.EventProperty;
import br.com.ablebit.eventz.domain.ProducerData;
import br.com.ablebit.eventz.domain.Rule;
import br.com.ablebit.eventz.engine.RuleProcessorVO;
import br.com.ablebit.eventz.service.AlarmLevelService;
import br.com.ablebit.eventz.service.AlarmService;

/**
 * Cria alarme e salva no banco de dados.
 * 
 * @author lfranchi
 *
 */
@Component
public class EventAlarm implements Processor {

	/**
	 * Logger.
	 */
	final static Logger LOGGER = LoggerFactory.getLogger(EventAlarm.class);

	@Autowired
	AlarmService alarmService;

	@Autowired
	AlarmLevelService alarmLevelService;

	@SuppressWarnings({ "unchecked" })
	@Override
	public void process(final Exchange exchange) throws Exception {

		final ProducerData data = exchange.getIn().getBody(RuleProcessorVO.class).getData();
		final Rule rule = exchange.getIn().getBody(RuleProcessorVO.class).getRule();
		final Map<EventProperty, String> eventProperties = (Map<EventProperty, String>) exchange.getIn()
				.getHeader("eventProperties");

		LOGGER.info(String.format("Criando alarme para %s", data));

		final AlarmLevel alarmLevel = alarmLevelService
				.findById(Long.valueOf(getPropertyValue(eventProperties, "alarmLevel")));

		final Alarm alarm = new Alarm();

		alarm.setDate(Calendar.getInstance());
		alarm.setFormula(rule.getFormula());
		alarm.setType(rule.getType());
		alarm.setDescription(
				String.format("Dado[%d] de %s[%s].", data.getId(), data.getProducer().getName(), data.getData()));
		alarm.setLevel(alarmLevel);

		alarmService.save(alarm);

	}

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
