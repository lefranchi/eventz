package br.com.lefranchi.eventz.domain;

import java.util.Arrays;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	 * Executada para o produtor.
	 */
	@OneToMany(mappedBy = "producerGroup", fetch = FetchType.EAGER)
	private Set<Rule> rules;

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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toStringExclude(this, Arrays.asList("rules", "producers"));
	}

}
