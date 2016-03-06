package br.com.ablebit.eventz.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.ablebit.eventz.domain.DataFieldMetadata;

public class DelimitedUtils {

	public static Map<String, Object> delimitedToMap(final String data, final Set<DataFieldMetadata> fields)
			throws JsonProcessingException, IOException {

		final Map<String, Object> mapValues = new HashMap<String, Object>();

		final String[] fieldValues = data.split(";");

		int i = 0;

		for (final DataFieldMetadata dataFieldMetadata : fields) {

			final String vl = fieldValues[i++];

			Object o = null;

			switch (dataFieldMetadata.getType()) {
			case NUMBER:
				try {
					o = Float.valueOf(vl.replace(',', '.'));
				} catch (final NumberFormatException e) {
					o = Long.valueOf(vl.replace(',', '.'));
				}
				break;

			case BOOLEAN:
				o = Boolean.valueOf(vl);

			default:
				o = vl;
			}

			mapValues.put(dataFieldMetadata.getName(), o);

		}

		return mapValues;
	}

}
