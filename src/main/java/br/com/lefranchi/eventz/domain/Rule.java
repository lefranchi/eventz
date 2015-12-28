package br.com.lefranchi.eventz.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@OneToMany
	private Set<Event> eventsOnTrue;

	/**
	 * Eventos que serão executados quando o resultados da formula for falso.
	 */
	@OneToMany
	private Set<Event> eventsOnFalse;

	/**
	 * Eventos que são sempre executados.
	 */
	@OneToMany
	private Set<Event> eventsOnAlways;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Set<Event> getEventsOnTrue() {
		return eventsOnTrue;
	}

	public void setEventsOnTrue(final Set<Event> eventsOnTrue) {
		this.eventsOnTrue = eventsOnTrue;
	}

	public Set<Event> getEventsOnFalse() {
		return eventsOnFalse;
	}

	public void setEventsOnFalse(final Set<Event> eventsOnFalse) {
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

	public Set<Event> getEventsOnAlways() {
		return eventsOnAlways;
	}

	public void setEventsOnAlways(final Set<Event> eventsOnAlways) {
		this.eventsOnAlways = eventsOnAlways;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
