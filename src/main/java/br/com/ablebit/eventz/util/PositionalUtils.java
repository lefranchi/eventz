package br.com.ablebit.eventz.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class PositionalUtils {

	public static String format(final String messageFormat, final Map<String, String> values) {

		final String[] fields = messageFormat.split("\\}");

		final StringBuilder sb = new StringBuilder();

		for (final String f : fields) {

			final String fieldColumns[] = f.substring(1).split("\\|");

			final String fieldName = fieldColumns[0];
			final Integer fieldSize = Integer.parseInt(fieldColumns[1]);
			final String fieldPadChar = fieldColumns[2];

			final String fieldValue = String.valueOf(values.get(fieldName));

			sb.append(StringUtils.leftPad(fieldValue, fieldSize, fieldPadChar));

		}

		return sb.toString();

	}

	public static void main(final String[] args) {

		final String messageFormat = "{lote|5|0}{nf|10|0}{volume|10|0}";
		final Map<String, String> values = new HashMap<>();
		values.put("lote", "2345");
		values.put("nf", "5574");
		values.put("volume", "5.45");

		System.out.println(format(messageFormat, values));

	}

}
