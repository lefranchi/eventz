package br.com.lefranchi.eventz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;

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

	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
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
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
