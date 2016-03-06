package br.com.ablebit.eventz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Campo recebido na mensagem enviada pelo Produtor.
 * 
 * @author lfranchi
 *
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "producer_metadata_id", "field_order" }) )
public class DataFieldMetadata extends AbstractPersistable<Long> implements Comparable<DataFieldMetadata> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5382007566348900518L;

	@ManyToOne
	@JoinColumn(name = "producer_metadata_id")
	private ProducerMetadata producerMetadata;

	/**
	 * Nome do campo.
	 */
	@Column
	private String name;

	/**
	 * Tipo de Dados do campo.
	 */
	@Enumerated(EnumType.STRING)
	private FieldDataType type;

	/**
	 * Ordem do Campo
	 */
	@Column(name = "field_order", nullable = false)
	private Integer order;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public FieldDataType getType() {
		return type;
	}

	public void setType(final FieldDataType type) {
		this.type = type;
	}

	public ProducerMetadata getProducerMetadata() {
		return producerMetadata;
	}

	public void setProducerMetadata(final ProducerMetadata producerMetadata) {
		this.producerMetadata = producerMetadata;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(final Integer order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@Override
	public int compareTo(final DataFieldMetadata o) {
		if (o.getOrder() == null)
			return -1;
		if (o.getOrder().equals(this.getOrder()))
			return -1;
		return -o.getOrder().compareTo(this.getOrder());
	}

}
