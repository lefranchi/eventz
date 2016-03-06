package br.com.ablebit.eventz.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * 
 * Metadados referentes aos dados produzidos pelo produtor.
 * 
 * @author lfranchi
 *
 */
@Entity
public class ProducerMetadata extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2214209733447526912L;

	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	/**
	 * Tipo de Dado que o produtor produzirá.
	 */
	@Enumerated(EnumType.STRING)
	private ProducerDataType dataType;

	/**
	 * Exemplo do dado que será recebido.
	 */
	@Column
	private String sampleData;

	/**
	 * Campos contidos no dado.
	 */
	@OneToMany(mappedBy = "producerMetadata", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("order ASC")
	private SortedSet<DataFieldMetadata> fields = new TreeSet<>();

	public String getSampleData() {
		return sampleData;
	}

	public void setSampleData(final String sampleData) {
		this.sampleData = sampleData;
	}

	public ProducerDataType getDataType() {
		return dataType;
	}

	public void setDataType(final ProducerDataType dataType) {
		this.dataType = dataType;
	}

	public SortedSet<DataFieldMetadata> getFields() {
		return fields;
	}

	public void setFields(final SortedSet<DataFieldMetadata> fields) {
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
