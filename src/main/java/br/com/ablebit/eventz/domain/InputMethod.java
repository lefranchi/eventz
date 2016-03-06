package br.com.ablebit.eventz.domain;

import java.util.Arrays;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Metodo de Entrada de dados.
 * 
 * @author lfranchi
 *
 */
@Entity
public class InputMethod extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2870911667641460657L;

	/**
	 * Nome.
	 */
	@Column(unique = true, nullable = false)
	private String name;

	/**
	 * Descrição.
	 */
	@Column
	private String description;

	/**
	 * Nome do componente.
	 */
	@Column(nullable = false)
	private String componentName;

	/**
	 * Lista de propriedades.
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> properties;

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

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(final String componentName) {
		this.componentName = componentName;
	}

	public Set<String> getProperties() {
		return properties;
	}

	public void setProperties(final Set<String> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toStringExclude(this, Arrays.asList("properties"));
	}

}
