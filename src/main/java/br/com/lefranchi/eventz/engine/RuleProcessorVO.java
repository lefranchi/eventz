package br.com.lefranchi.eventz.engine;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import br.com.lefranchi.eventz.domain.ProducerData;
import br.com.lefranchi.eventz.domain.Rule;

public class RuleProcessorVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 96802370230087851L;

	private Rule rule;
	private ProducerData data;
	private Boolean result;

	public Rule getRule() {
		return rule;
	}

	public void setRule(final Rule rule) {
		this.rule = rule;
	}

	public ProducerData getData() {
		return data;
	}

	public void setData(final ProducerData data) {
		this.data = data;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(final Boolean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
