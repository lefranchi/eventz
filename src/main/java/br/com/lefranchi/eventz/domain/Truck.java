package br.com.lefranchi.eventz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Truck extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3617134905539574820L;

	@Column(unique = true)
	private String identification;

	@Column(unique = true)
	private String plate;

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
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
