package br.com.lefranchi.eventz.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

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

	@OneToMany(fetch = FetchType.LAZY)
	private Set<Nf> nfs;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public Set<Nf> getNfs() {
		return nfs;
	}

	public void setNfs(final Set<Nf> nfs) {
		this.nfs = nfs;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
