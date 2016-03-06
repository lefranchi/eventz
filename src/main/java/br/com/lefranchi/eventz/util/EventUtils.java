package br.com.lefranchi.eventz.util;

import java.util.Map;

import br.com.lefranchi.eventz.domain.EventProperty;

public class EventUtils {

	public static String getPropertyValue(final Map<EventProperty, String> eventProperties, final String name) {

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
