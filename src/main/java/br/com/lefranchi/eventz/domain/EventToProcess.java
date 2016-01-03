package br.com.lefranchi.eventz.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class EventToProcess extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4029247530376983875L;

	/**
	 * Evento.
	 */
	@ManyToOne(optional = false)
	private Event event;

	/**
	 * Propriedades do Evento.
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "property")
	@Column(name = "value")
	private Map<EventProperty, String> properties = new HashMap<>();

	public Event getEvent() {
		return event;
	}

	public void setEvent(final Event event) {
		this.event = event;
	}

	public Map<EventProperty, String> getProperties() {
		return properties;
	}

	public void setProperties(final Map<EventProperty, String> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
