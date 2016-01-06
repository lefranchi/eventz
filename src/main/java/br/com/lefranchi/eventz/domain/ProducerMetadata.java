package br.com.lefranchi.eventz.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

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
	@OneToMany(mappedBy = "producerMetadata", cascade = CascadeType.ALL)
	private Set<DataFieldMetadata> fields;

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

	public Set<DataFieldMetadata> getFields() {
		return fields;
	}

	public void setFields(final Set<DataFieldMetadata> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
