package br.com.lefranchi.eventz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Evento que será executado.
 * 
 * @author lfranchi
 *
 */
@Entity
public class Event extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7783198743654690029L;

	/**
	 * Nome do Evento.
	 */
	@Column(unique = true, nullable = false)
	private String name;

	/**
	 * Classe processadora do Evento. Será instanciada e executada via
	 * reflection.
	 */
	@Column(unique = true, nullable = false)
	private String processor;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(final String processor) {
		this.processor = processor;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
