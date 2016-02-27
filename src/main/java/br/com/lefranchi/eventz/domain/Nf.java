package br.com.lefranchi.eventz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NamedQuery(name = "listAllNotProcessed", query = "select n from Nf n where n.processed = 0")
public class Nf extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7474141683881464425L;

	@ManyToOne
	@JoinColumn(name = "caminhao_id")
	private Caminhao caminhao;

	@Column(nullable = false)
	private Integer lote;

	@Column(nullable = false)
	private Integer nf;

	@Column(nullable = false)
	private float volume;

	@Column(nullable = false)
	private Boolean processed = Boolean.FALSE;

	public Caminhao getCaminhao() {
		return caminhao;
	}

	public void setCaminhao(final Caminhao caminhao) {
		this.caminhao = caminhao;
	}

	public Integer getLote() {
		return lote;
	}

	public void setLote(final Integer lote) {
		this.lote = lote;
	}

	public Integer getNf() {
		return nf;
	}

	public void setNf(final Integer nf) {
		this.nf = nf;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(final float volume) {
		this.volume = volume;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(final Boolean processed) {
		this.processed = processed;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
