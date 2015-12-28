package br.com.lefranchi.eventz.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lefranchi.eventz.domain.Event;
import br.com.lefranchi.eventz.domain.FormulaType;
import br.com.lefranchi.eventz.domain.ProducerData;
import br.com.lefranchi.eventz.domain.ProducerDataType;
import br.com.lefranchi.eventz.domain.Rule;
import br.com.lefranchi.eventz.engine.event.EventProcessor;
import br.com.lefranchi.eventz.util.DelimitedUtils;
import br.com.lefranchi.eventz.util.JsonUtils;

public class RuleEngine {

	final static Logger logger = LoggerFactory.getLogger(RuleEngine.class);

	private static final JexlEngine jexl = new JexlEngine();

	static {
		jexl.setCache(512);
		jexl.setLenient(false);
		jexl.setSilent(false);
	}

	public void processRule(final Rule rule, final ProducerData data) throws Exception {

		Boolean retValue = true;

		logger.debug(String.format("[%s:%d] Inicio de execução de regra %s para o dado %s", rule.getName(),
				rule.getId(), rule, data));

		logger.debug(String.format("[%s:%d] Extraindo variaveis...", rule.getName(), rule.getId()));

		Map<String, Object> mapValues = new HashMap<>();
		if (data.getProducer().getMetadata().getDataType().equals(ProducerDataType.DELIMITED)) {
			mapValues = DelimitedUtils.delimitedToMap(data.getData(), data.getProducer().getMetadata().getFields());
		} else if (data.getProducer().getMetadata().getDataType().equals(ProducerDataType.JSON)) {
			mapValues = JsonUtils.jsonToMap(data.getData());
		} else if (data.getProducer().getMetadata().getDataType().equals(ProducerDataType.XML)) {
			// TODO: Implementar processamento de regras para xml
			logger.error("Erro extraindo variaveus do tipo XML.");
			throw new Exception("Tipo de execução de regra não implementada.");
		}

		logger.debug(String.format("[%s:%d] Variaveis Extraidas: %s", rule.getName(), rule.getId(), mapValues));

		logger.debug(String.format("[%s:%d] Inicio de processamento da regra...", rule.getName(), rule.getId()));

		if (rule.getType().equals(FormulaType.JEXL)) {
			retValue = executeJEXL(rule, mapValues);
		} else if (rule.getType().equals(FormulaType.EL)) {
			// TODO: Implementar processamento de regras para expression
			// language
			throw new Exception("Tipo de execução de regra não implementada [EL].");
		} else if (rule.getType().equals(FormulaType.JS)) {
			// TODO: Implementar processamento de regras para JavaScript
			throw new Exception("Tipo de execução de regra não implementada [JS].");
		} else {
			throw new Exception("Tipo de execução de regra não implementada.");
		}

		logger.debug(String.format("[%s:%d] Regra processada com resultado igual a %b", rule.getName(), rule.getId(),
				retValue));

		logger.debug(String.format("[%s:%d] Inicio de eventos %s. Total de %d eventos.", rule.getName(), rule.getId(),
				(retValue ? "positivas" : "negativas"), rule.getEventsOnTrue().size()));

		executeEvent(rule, data, (retValue ? rule.getEventsOnTrue() : rule.getEventsOnFalse()));

		logger.debug(String.format("[%s:%d] Inicio de eventos Always. Total de %d eventos.", rule.getName(),
				rule.getId(), rule.getEventsOnAlways().size()));

		executeEvent(rule, data, rule.getEventsOnAlways());

	}

	private Boolean executeJEXL(final Rule rule, final Map<String, Object> mapValues) {
		Boolean retValue;
		logger.debug("Iniciando execução de JEXL");

		final Expression e = jexl.createExpression(rule.getFormula());
		final JexlContext context = new MapContext();
		for (final String key : mapValues.keySet()) {
			context.set(key, mapValues.get(key));
		}
		retValue = (Boolean) e.evaluate(context);
		return retValue;
	}

	private void executeEvent(final Rule rule, final ProducerData data, final Set<Event> events)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		for (final Event event : events) {

			logger.debug(String.format("[%s:%d] Inicio de execução do evento %s:%d", rule.getName(), rule.getId(),
					event.getName(), event.getId()));

			final EventProcessor processor = (EventProcessor) Class.forName(event.getProcessor()).newInstance();
			processor.process(data, rule);

			logger.debug(String.format("[%s:%d] Evento %s:%d processado", rule.getName(), rule.getId(), event.getName(),
					event.getId()));

		}

	}
}
