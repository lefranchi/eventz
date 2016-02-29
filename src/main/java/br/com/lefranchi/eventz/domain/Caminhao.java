package br.com.lefranchi.eventz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Caminhao extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3617134905539574820L;

	@Column(unique = true)
	private String identificador;

	@Column(unique = true)
	private String placa;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(final String placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
