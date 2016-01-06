package br.com.lefranchi.eventz.domain;

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
	 * Metadados referentes ao tipo de dado produzido pelo Produtor.
	 */
	@ManyToOne(optional = false)
	private ProducerMetadata metadata;

	/**
	 * Executada para o produtor.
	 */
	@OneToMany(mappedBy = "producer", fetch = FetchType.EAGER)
	private Set<Rule> rules;

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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toStringExclude(this, Arrays.asList("rules"));
	}

}
