package br.com.ablebit.eventz.engine;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.ablebit.eventz.domain.FormulaType;
import br.com.ablebit.eventz.domain.ProducerData;
import br.com.ablebit.eventz.domain.Rule;
import br.com.ablebit.eventz.util.DataValueUtils;

@Component
public class RuleProcessor implements Processor {

	final static Logger LOGGER = LoggerFactory.getLogger(RuleProcessor.class);

	private static final JexlEngine jexl = new JexlEngine();

	static {
		jexl.setCache(512);
		jexl.setLenient(false);
		jexl.setSilent(false);
	}

	private Boolean executeJEXL(final Rule rule, final Map<String, Object> mapValues) throws Exception {
		Boolean retValue;
		LOGGER.debug("Iniciando execução de JEXL");

		final Expression e = jexl.createExpression(rule.getFormula());
		final JexlContext context = new MapContext();
		for (final String key : mapValues.keySet()) {
			context.set(key, mapValues.get(key));
		}
		retValue = (Boolean) e.evaluate(context);
		return retValue;
	}

	@Override
	public void process(final Exchange exchange) throws Exception {

		final RuleProcessorVO processorVO = exchange.getIn().getBody(RuleProcessorVO.class);
		final Rule rule = processorVO.getRule();
		final ProducerData data = processorVO.getData();

		Boolean retValue = true;

		LOGGER.debug(String.format("[%s:%d] Inicio de execução de regra %s para o dado %s", rule.getName(),
				rule.getId(), rule, data));

		LOGGER.debug(String.format("[%s:%d] Extraindo variaveis...", rule.getName(), rule.getId()));

		final Map<String, Object> dataValues = DataValueUtils.extractDataValues(data);

		LOGGER.debug(String.format("[%s:%d] Variaveis Extraidas: %s", rule.getName(), rule.getId(), dataValues));

		LOGGER.debug(String.format("[%s:%d] Inicio de processamento da regra...", rule.getName(), rule.getId()));

		if (rule.getType().equals(FormulaType.JEXL)) {
			retValue = executeJEXL(rule, dataValues);
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

		LOGGER.debug(String.format("[%s:%d] Regra processada com resultado igual a %b", rule.getName(), rule.getId(),
				retValue));

		processorVO.setResult(retValue);

	}

}
