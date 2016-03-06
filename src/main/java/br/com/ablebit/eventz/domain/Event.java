package br.com.ablebit.eventz.domain;

import java.util.Arrays;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

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
	 * Classe processadora do Evento.
	 */
	@Column(unique = true, nullable = false)
	private String processor;

	/**
	 * Lista de propriedades do Evento. No momento da configuração será
	 * utilizado para configurar a evento a ser executado.
	 */
	@OneToMany(mappedBy = "event")
	private Set<EventProperty> properties;

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

	public Set<EventProperty> getProperties() {
		return properties;
	}

	public void setProperties(final Set<EventProperty> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toStringExclude(this, Arrays.asList("properties"));
	}

}
