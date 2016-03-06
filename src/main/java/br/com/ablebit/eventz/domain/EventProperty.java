package br.com.ablebit.eventz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Propriedade do Evento.
 * 
 * @author lfranchi
 *
 */
@Entity
public class EventProperty extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7580153902942531656L;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING) // TODO NOT NULL
	private EventPropertyType type;

	@Column(nullable = false)
	private boolean mandatory;

	@ManyToOne(optional = false)
	private Event event;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public EventPropertyType getType() {
		return type;
	}

	public void setType(final EventPropertyType type) {
		this.type = type;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(final Event event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
