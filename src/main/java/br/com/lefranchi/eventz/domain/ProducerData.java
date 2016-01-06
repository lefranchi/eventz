package br.com.lefranchi.eventz.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

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

	/**
	 * Variaveis e valores extraidos.
	 */
	@ElementCollection
	@MapKeyColumn(name = "data_key")
	@Column(name = "data_value")
	@CollectionTable(name = "producer_data_values", joinColumns = @JoinColumn(name = "producer_data_id") )
	private Map<String, String> dataValues = new HashMap<String, String>();

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

	public Map<String, String> getDataValues() {
		return dataValues;
	}

	public void setDataValues(final Map<String, String> dataValues) {
		this.dataValues = dataValues;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
