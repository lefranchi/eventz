package br.com.ablebit.eventz.domain;

import java.util.Arrays;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Produtor de Dados.
 * 
 * @author lfranchi
 *
 */
@Entity
public class Producer extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3589065248968006521L;

	/**
	 * Nome do Produtor.
	 */
	@Column(unique = true)
	private String name;

	/**
	 * Grupo do Produtor.
	 */
	@ManyToOne
	private ProducerGroup producerGroup;

	/**
	 * Metadados referentes ao tipo de dado produzido pelo Produtor.
	 */
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private ProducerMetadata metadata;

	/**
	 * Executada para o produtor.
	 */
	@OneToMany(mappedBy = "producer", fetch = FetchType.EAGER)
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

	/**
	 * Metodo de entrada dos dados.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	private ProducerInputMethod inputMethod;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ProducerMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(final ProducerMetadata metadata) {
		this.metadata = metadata;
	}

	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(final Set<Rule> rules) {
		this.rules = rules;
	}

	public Set<EventToProcess> getEventsOnException() {
		return eventsOnException;
	}

	public void setEventsOnException(final Set<EventToProcess> eventsOnException) {
		this.eventsOnException = eventsOnException;
	}

	public ProducerGroup getProducerGroup() {
		return producerGroup;
	}

	public void setProducerGroup(final ProducerGroup producerGroup) {
		this.producerGroup = producerGroup;
	}

	public Set<EventToProcess> getEventsOnAlways() {
		return eventsOnAlways;
	}

	public void setEventsOnAlways(final Set<EventToProcess> eventsOnAlways) {
		this.eventsOnAlways = eventsOnAlways;
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
				Arrays.asList("rules", "eventsOnAlways", "eventsOnException"));
	}

}
