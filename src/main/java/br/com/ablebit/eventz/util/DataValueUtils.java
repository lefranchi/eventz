package br.com.ablebit.eventz.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.ablebit.eventz.domain.ProducerData;
import br.com.ablebit.eventz.domain.ProducerDataType;

public class DataValueUtils {

	final static Logger LOGGER = LoggerFactory.getLogger(DataValueUtils.class);

	public static Map<String, Object> extractDataValues(final ProducerData data)
			throws JsonProcessingException, IOException, Exception {

		Map<String, Object> dataValues = new HashMap<>();
		if (data.getProducer().getMetadata().getDataType().equals(ProducerDataType.DELIMITED)) {
			dataValues = DelimitedUtils.delimitedToMap(data.getData(), data.getProducer().getMetadata().getFields());
		} else if (data.getProducer().getMetadata().getDataType().equals(ProducerDataType.JSON)) {
			dataValues = JsonUtils.jsonToMap(data.getData());
		} else if (data.getProducer().getMetadata().getDataType().equals(ProducerDataType.XML)) {
			// TODO: Implementar processamento de regras para xml
			LOGGER.error("Erro extraindo variaveis do tipo XML.");
			throw new Exception("Tipo de execução de regra não implementada.");
		}

		data.setDataValues(new HashMap<>());
		dataValues.forEach((key, value) -> {
			data.getDataValues().put(key, String.valueOf(value));
		});

		return dataValues;

	}

}
