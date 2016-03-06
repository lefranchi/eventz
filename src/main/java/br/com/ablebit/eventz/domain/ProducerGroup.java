package br.com.ablebit.eventz.domain;

import java.util.Arrays;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Agrupamento de Produtores.
 * 
 * @author lfranchi
 *
 */
@Entity
public class ProducerGroup extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 739871589708569199L;

	/**
	 * Nome do Grupo de Produtor.
	 */
	@Column(unique = true)
	private String name;

	/**
	 * Produtores.
	 */
	@OneToMany(mappedBy = "producerGroup", fetch = FetchType.EAGER)
	private Set<Producer> producers;

	/**
	 * Metodo de entrada dos dados.
	 */
	@ManyToOne
	private ProducerInputMethod inputMethod;

	/**
	 * Executada para o produtor.
	 */
	@OneToMany(mappedBy = "producerGroup", fetch = FetchType.EAGER)
	private Set<Rule> rules;

	/**
	 * Eventos executados sempre.
	 */
	@OneToMany(fetch = FetchType.EAGER)
	private Set<EventToProcess> eventsOnAlways;

	/**
	 * Eventos executados em erro de leitura de dados externos/internos.
	 */
	@OneToMany(fetch = FetchType.EAGER)
	private Set<EventToProcess> eventsOnException;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Set<Producer> getProducers() {
		return producers;
	}

	public void setProducers(final Set<Producer> producers) {
		this.producers = producers;
	}

	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(final Set<Rule> rules) {
		this.rules = rules;
	}

	public Set<EventToProcess> getEventsOnAlways() {
		return eventsOnAlways;
	}

	public void setEventsOnAlways(final Set<EventToProcess> eventsOnAlways) {
		this.eventsOnAlways = eventsOnAlways;
	}

	public Set<EventToProcess> getEventsOnException() {
		return eventsOnException;
	}

	public void setEventsOnException(final Set<EventToProcess> eventsOnException) {
		this.eventsOnException = eventsOnException;
	}

	public ProducerInputMethod getInputMethod() {
		return inputMethod;
	}

	public void setInputMethod(final ProducerInputMethod inputMethod) {
		this.inputMethod = inputMethod;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toStringExclude(this,
				Arrays.asList("rules", "producers", "eventsOnAlways", "eventsOnException"));
	}

}
