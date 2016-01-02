package br.com.lefranchi.eventz.domain;

import java.util.Arrays;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	@OneToOne(mappedBy = "producer", cascade = CascadeType.ALL)
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
	private Set<Event> eventsOnException;

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

	public Set<Event> getEventsOnException() {
		return eventsOnException;
	}

	public void setEventsOnException(final Set<Event> eventsOnException) {
		this.eventsOnException = eventsOnException;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toStringExclude(this, Arrays.asList("rules"));
	}

}
