package br.com.ablebit.eventz.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	public static Map<String, Object> jsonToMap(String json) throws JsonProcessingException, IOException {

		Map<String, Object> mapValues = new HashMap<String, Object>();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json.getBytes());

		Iterator<String> names = rootNode.fieldNames();
		while (names.hasNext()) {
			String name = names.next();
			mapValues.put(name, rootNode.path(name));
		}

		return mapValues;

	}

}
