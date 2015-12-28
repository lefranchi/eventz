package br.com.lefranchi.eventz.engine;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.lefranchi.eventz.domain.DataFieldMetadata;
import br.com.lefranchi.eventz.domain.Event;
import br.com.lefranchi.eventz.domain.FieldDataType;
import br.com.lefranchi.eventz.domain.FormulaType;
import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.domain.ProducerData;
import br.com.lefranchi.eventz.domain.ProducerDataType;
import br.com.lefranchi.eventz.domain.ProducerMetadata;
import br.com.lefranchi.eventz.domain.Rule;

public class RuleEngineTest {

	@Test
	public void rules() {

		// Quem produz o dado.
		final Producer producer = new Producer();
		producer.setName("Bomba1");

		// Metadados do Produtor.
		final ProducerMetadata producerMetadata = new ProducerMetadata();
		producerMetadata.setProducer(producer);
		producerMetadata.setDataType(ProducerDataType.DELIMITED);
		producerMetadata.setSampleData("88745;LEANDRO FRANCHI;1979;1.25");

		producerMetadata.setFields(new LinkedHashSet<>());

		final DataFieldMetadata dataFieldMetadataID = new DataFieldMetadata();
		dataFieldMetadataID.setName("ID");
		dataFieldMetadataID.setType(FieldDataType.NUMBER);
		producerMetadata.getFields().add(dataFieldMetadataID);

		final DataFieldMetadata dataFieldMetadataNOME = new DataFieldMetadata();
		dataFieldMetadataNOME.setName("NOME");
		dataFieldMetadataNOME.setType(FieldDataType.STRING);
		producerMetadata.getFields().add(dataFieldMetadataNOME);

		final DataFieldMetadata dataFieldMetadataANO = new DataFieldMetadata();
		dataFieldMetadataANO.setName("ANO");
		dataFieldMetadataANO.setType(FieldDataType.NUMBER);
		producerMetadata.getFields().add(dataFieldMetadataANO);

		producer.setMetadata(producerMetadata);

		// O proprio dado proveniente do Produtor.
		final ProducerData data = new ProducerData();
		data.setProducer(producer);
		// data.setData("{\"name\":\"jose\", \"idade\":\"37\"}");
		data.setData("25;PEDRO FRANCHI;2008;1.85");

		// A regra que vai ser aplicada para este produtor.
		final Rule rule = new Rule();
		rule.setName("Regra1");
		rule.setProducer(producer);
		rule.setType(FormulaType.JEXL);
		rule.setFormula("ANO > 2010");

		// Eventos processados para retorno verdadeiro.
		final Set<Event> eventsOnTrue = new HashSet<Event>();
		final Event e1 = new Event();
		e1.setName("Evento 1");
		e1.setProcessor("br.com.lefranchi.events.event.log.EventLog");
		eventsOnTrue.add(e1);

		rule.setEventsOnTrue(eventsOnTrue);

		final Set<Event> eventsOnFalse = new HashSet<Event>();
		final Event e2 = new Event();
		e2.setName("Evento Alarm");
		e2.setProcessor("br.com.lefranchi.events.event.alarm.EventAlarm");
		eventsOnFalse.add(e2);

		rule.setEventsOnFalse(eventsOnFalse);

		final RuleEngine ruleEngine = new RuleEngine();

		try {
			ruleEngine.processRule(rule, data);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			assert false;
		} catch (final InstantiationException e) {
			e.printStackTrace();
			assert false;
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
			assert false;
		} catch (final JsonProcessingException e) {
			e.printStackTrace();
			assert false;
		} catch (final IOException e) {
			e.printStackTrace();
			assert false;
		} catch (final Exception e) {
			e.printStackTrace();
			assert false;
		}

	}

}
