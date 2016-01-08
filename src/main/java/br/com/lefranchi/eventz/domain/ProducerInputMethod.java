package br.com.lefranchi.eventz.domain;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Metodo de Entrada do Produtor.
 * 
 * @author lfranchi
 *
 */
@Entity
public class ProducerInputMethod extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5998037417275479987L;

	@ManyToOne(optional = false)
	private InputMethod inputMethod;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "property")
	@Column(name = "value")
	private Map<String, String> properties;

	public InputMethod getInputMethod() {
		return inputMethod;
	}

	public void setInputMethod(final InputMethod inputMethod) {
		this.inputMethod = inputMethod;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(final Map<String, String> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
