package br.com.lefranchi.eventz.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Alarme.
 * 
 * @author lfranchi
 *
 */
@Entity
public class Alarm extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4979456818483282489L;

	/**
	 * Data que o alarme foi gerado.
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;

	/**
	 * Dado que gerou o alarme.
	 */
	@ManyToOne
	private ProducerData producerData;

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

	public Calendar getDate() {
		return date;
	}

	public void setDate(final Calendar date) {
		this.date = date;
	}

	public ProducerData getProducerData() {
		return producerData;
	}

	public void setProducerData(final ProducerData producerData) {
		this.producerData = producerData;
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

	public void setFormula(final String formula) {
		this.formula = formula;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
