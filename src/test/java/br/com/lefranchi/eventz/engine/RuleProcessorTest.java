package br.com.lefranchi.eventz.engine;

import java.util.HashSet;
import java.util.LinkedHashSet;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.lefranchi.eventz.Application;
import br.com.lefranchi.eventz.domain.AlarmLevel;
import br.com.lefranchi.eventz.domain.Event;
import br.com.lefranchi.eventz.domain.EventProperty;
import br.com.lefranchi.eventz.domain.EventPropertyType;
import br.com.lefranchi.eventz.domain.EventToProcess;
import br.com.lefranchi.eventz.domain.FormulaType;
import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.domain.Rule;
import br.com.lefranchi.eventz.repository.ProducerRepository;
import br.com.lefranchi.eventz.repository.RuleRepository;
import br.com.lefranchi.eventz.service.AlarmLevelService;
import br.com.lefranchi.eventz.service.RouteService;
import br.com.lefranchi.eventz.testutils.ProducerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class RuleProcessorTest {

	@Autowired
	CamelContext camelContext;

	@EndpointInject(uri = "activemq:Producer-01")
	ProducerTemplate producerTemplate;

	@Autowired
	ProducerRepository producerRepository;
	Producer producer;

	@Autowired
	RuleRepository ruleRepository;

	@Autowired
	RouteService routeService;

	@Autowired
	AlarmLevelService alarmLevelService;
	AlarmLevel alarmLevel;

	@Before
	public void setUp() {

		alarmLevel = new AlarmLevel();
		alarmLevel.setName("Informacao");
		alarmLevel.setColor("blue");
		alarmLevel.setImage("info.png");

		alarmLevel = alarmLevelService.save(alarmLevel);

		producer = ProducerTestUtils.newProducer();

		// Eventos para erro de execução de rota.
		producer.setEventsOnException(new HashSet<>());
		final Event e0 = new Event();
		e0.setName("Evento 0");
		e0.setProcessor("eventLog");
		final EventToProcess eventToProcess0 = new EventToProcess();
		eventToProcess0.setEvent(e0);
		producer.getEventsOnException().add(eventToProcess0);

		// A regra que vai ser aplicada para este produtor.
		Rule rule = new Rule();
		rule.setName("Regra1");
		rule.setProducer(producer);
		rule.setType(FormulaType.JEXL);
		rule.setFormula("content > 4");

		// Eventos processados para retorno verdadeiro.
		rule.setEventsOnTrue(new HashSet<>());
		final Event e1 = new Event();
		e1.setName("Evento 1");
		e1.setProcessor("eventLog");
		final EventToProcess eventToProcess1 = new EventToProcess();
		eventToProcess1.setEvent(e1);
		rule.getEventsOnTrue().add(eventToProcess1);

		// Eventos processados para retorno falso.
		rule.setEventsOnFalse(new HashSet<>());
		final Event e2 = new Event();
		e2.setName("Evento Alarm");
		e2.setProcessor("eventAlarm");
		final EventToProcess eventToProcess2 = new EventToProcess();
		eventToProcess2.setEvent(e2);
		final EventProperty eventProperty = new EventProperty();
		eventProperty.setEvent(e2);
		eventProperty.setMandatory(true);
		eventProperty.setName("alarmLevel");
		eventProperty.setType(EventPropertyType.ALARM_LEVEL);
		eventToProcess2.getProperties().put(eventProperty, String.valueOf(alarmLevel.getId()));
		rule.getEventsOnFalse().add(eventToProcess2);

		producer.setRules(new LinkedHashSet<>());
		producer.getRules().add(rule);

		rule.setProducer(producer);

		producer = producerRepository.save(producer);

		rule = ruleRepository.save(rule);

	}

	@Test
	public void basicRuleTest() {

		try {
			routeService.loadRoute(producer);
		} catch (final Exception e) {
			assert false;
			e.printStackTrace();
			return;
		}

		producerTemplate.sendBody(ProducerTestUtils.PRODUCER_SAMPLE_DATA);

	}

	/*
	 * @Test public void parseErrorRuleTest() {
	 * 
	 * try { routeService.loadRoute(producer); } catch (final Exception e) {
	 * assert false; e.printStackTrace(); return; }
	 * 
	 * producerTemplate.sendBody(ProducerTestUtils.PRODUCER_SAMPLE_DATA.
	 * substring(0, 10));
	 */
	// TODO: CONFIGURAR PARA GERAR ALARME E TESTAR SE ELE FOI GERADO.

	// TODO: TESTAR FORMATO INVALIDO.

	// TODO : TESTAR MENSAGEM COM MENOS PARAMETROS.

	// TODO: TESTAR PARAMETROS NA REGRA INVALIDOS.

	// }

}
