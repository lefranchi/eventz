package br.com.lefranchi.eventz.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.lefranchi.eventz.domain.DataFieldMetadata;

public class DelimitedUtils {

	public static Map<String, Object> delimitedToMap(final String data, final Set<DataFieldMetadata> fields)
			throws JsonProcessingException, IOException {

		final Map<String, Object> mapValues = new HashMap<String, Object>();

		final String[] fieldValues = data.split(";");

		int i = 0;

		for (final DataFieldMetadata dataFieldMetadata : fields) {

			mapValues.put(dataFieldMetadata.getName(), fieldValues[i++]);

		}

		return mapValues;
	}

}
