package br.com.lefranchi.eventz.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.camel.Exchange;
import org.apache.camel.component.jpa.Consumed;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NamedQuery(name = "listAllNotProcessed", query = "select n from Nf n where n.processed = 0")
public class Nf extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7474141683881464425L;

	@NotNull(message = "Caminhão não pode estar vazio.")
	@ManyToOne
	@JoinColumn(name = "truck_id")
	private Truck truck;

	@NotNull(message = "Lote não pode estar vazio.")
	@Min(value = 1, message = "Lote não pode ser 0.")
	@Column(nullable = false)
	private Integer lote;

	@NotNull(message = "NF não pode estar vazia.")
	@Min(value = 1, message = "NF não pode ser 0.")
	@Column(nullable = false)
	private Integer nf;

	@NotNull(message = "Volume não pode estar vazio.")
	@DecimalMin(value = "1.00", message = "Volume não pode ser 0.")
	@Column(nullable = false)
	private BigDecimal volume;

	@Column(nullable = false)
	private Boolean processed = Boolean.FALSE;

	@Consumed
	public void executeConsumed(final Exchange exchange) {
		this.setProcessed(Boolean.TRUE);
	}

	public Truck getTruck() {
		return truck;
	}

	public void setTruck(final Truck truck) {
		this.truck = truck;
	}

	public Integer getLote() {
		return lote;
	}

	public void setLote(final Integer lote) {
		this.lote = lote;
	}

	public Integer getNf() {
		return nf;
	}

	public void setNf(final Integer nf) {
		this.nf = nf;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(final BigDecimal volume) {
		this.volume = volume;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(final Boolean processed) {
		this.processed = processed;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
