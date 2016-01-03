package br.com.lefranchi.eventz.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

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
	@ElementCollection
	@MapKeyColumn(name = "property")
	@Column(name = "value")
	@CollectionTable(name = "event_properties", joinColumns = @JoinColumn(name = "event_to_process_id") )
	private Map<EventProperty, Object> properties = new HashMap<>();

	public Event getEvent() {
		return event;
	}

	public void setEvent(final Event event) {
		this.event = event;
	}

	public Map<EventProperty, Object> getProperties() {
		return properties;
	}

	public void setProperties(final Map<EventProperty, Object> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
