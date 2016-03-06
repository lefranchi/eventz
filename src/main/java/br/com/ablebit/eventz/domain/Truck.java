package br.com.ablebit.eventz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Truck extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3617134905539574820L;

	@NotNull(message = "Idenficação não pode estar vazia.")
	@NotEmpty(message = "Idenficação não pode estar vazia.")
	@Size(min = 2, max = 30, message = "Idenficação deve conter entre 2 e 30 caracteres.")
	@Column(unique = true)
	private String identification;

	@NotNull(message = "Placa não pode estar vazia.")
	@NotEmpty(message = "Placa não pode estar vazia.")
	@Size(min = 8, max = 8, message = "Placa deve conter 8 caracteres.")
	@Column(unique = true)
	private String plate;

	public String getIdentificationPlate() {
		return getIdentification() + " / " + getPlate();
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(final String identification) {
		this.identification = identification;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(final String plate) {
		this.plate = plate;
	}

	@Override
	public void setId(final Long id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
