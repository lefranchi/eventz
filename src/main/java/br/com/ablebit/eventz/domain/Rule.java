package br.com.ablebit.eventz.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Regra.
 * 
 * @author lfranchi
 *
 */
@Entity
public class Rule extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1325837928617133081L;

	/**
	 * Nome da Regra.
	 */
	@Column(unique = true, nullable = false)
	private String name;

	/**
	 * Executada para o produtor.
	 */
	@ManyToOne
	private Producer producer;

	/**
	 * Executada para o grupo do produtor.
	 */
	@ManyToOne
	private ProducerGroup producerGroup;

	/**
	 * Tipo de Formula.
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private FormulaType type;

	/**
	 * Implementação da Formula.
	 */
	@Column(nullable = false)
	private String formula;

	/**
	 * Eventos que serão executados quando o resultado da formula for
	 * verdadeiro.
	 */
	@OneToMany(fetch = FetchType.EAGER)
	private Set<EventToProcess> eventsOnTrue;

	/**
	 * Eventos que serão executados quando o resultados da formula for falso.
	 */
	@OneToMany(fetch = FetchType.EAGER)
	private Set<EventToProcess> eventsOnFalse;

	/**
	 * Eventos que são sempre executados.
	 */
	@OneToMany(fetch = FetchType.EAGER)
	private Set<EventToProcess> eventsOnAlways;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Set<EventToProcess> getEventsOnTrue() {
		return eventsOnTrue;
	}

	public void setEventsOnTrue(final Set<EventToProcess> eventsOnTrue) {
		this.eventsOnTrue = eventsOnTrue;
	}

	public Set<EventToProcess> getEventsOnFalse() {
		return eventsOnFalse;
	}

	public void setEventsOnFalse(final Set<EventToProcess> eventsOnFalse) {
		this.eventsOnFalse = eventsOnFalse;
	}

	public FormulaType getType() {
		return type;
	}

	public void setType(final FormulaType type) {
		this.type = type;
	}

	public String getFormula() {
		return formula;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(final Producer producer) {
		this.producer = producer;
	}

	public void setFormula(final String formula) {
		this.formula = formula;
	}

	public Set<EventToProcess> getEventsOnAlways() {
		return eventsOnAlways;
	}

	public void setEventsOnAlways(final Set<EventToProcess> eventsOnAlways) {
		this.eventsOnAlways = eventsOnAlways;
	}

	public ProducerGroup getProducerGroup() {
		return producerGroup;
	}

	public void setProducerGroup(final ProducerGroup producerGroup) {
		this.producerGroup = producerGroup;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
