package br.com.lefranchi.eventz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * 
 * Dado recebido do Produtor.
 * 
 * @author lfranchi
 *
 */
@Entity
public class ProducerData extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9200872670056654091L;

	/**
	 * Produtor do Dado.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private Producer producer;

	/**
	 * Dado recebido.
	 */
	@Column(nullable = false)
	private String data;

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(final Producer producer) {
		this.producer = producer;
	}

	public String getData() {
		return data;
	}

	public void setData(final String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
