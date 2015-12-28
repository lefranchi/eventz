package br.com.lefranchi.eventz.engine.event;

import br.com.lefranchi.eventz.domain.ProducerData;
import br.com.lefranchi.eventz.domain.Rule;

/**
 * Processador de Eventos.
 * 
 * @author lfranchi
 *
 */
public abstract class EventProcessor {

	/**
	 * Processa o Evento.
	 * 
	 * @param data
	 */
	public abstract void process(ProducerData data, Rule rule);

}
