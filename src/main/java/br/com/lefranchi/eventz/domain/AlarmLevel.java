package br.com.lefranchi.eventz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class AlarmLevel extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4381378262015472344L;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String image;

	@Column(nullable = false)
	private String color;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public String getColor() {
		return color;
	}

	public void setColor(final String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
