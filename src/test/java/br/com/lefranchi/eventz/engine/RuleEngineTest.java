package br.com.lefranchi.eventz.engine;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
import br.com.lefranchi.eventz.domain.Event;
import br.com.lefranchi.eventz.domain.FormulaType;
import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.domain.Rule;
import br.com.lefranchi.eventz.repository.ProducerRepository;
import br.com.lefranchi.eventz.repository.RuleRepository;
import br.com.lefranchi.eventz.service.RouteService;
import br.com.lefranchi.eventz.testutils.ProducerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class RuleEngineTest {

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

	@Before
	public void setUp() {

		producer = ProducerTestUtils.newProducer();

		// A regra que vai ser aplicada para este produtor.
		Rule rule = new Rule();
		rule.setName("Regra1");
		rule.setProducer(producer);
		rule.setType(FormulaType.JEXL);
		rule.setFormula("ANO > 2010");

		// Eventos processados para retorno verdadeiro.
		final Set<Event> eventsOnTrue = new HashSet<Event>();
		final Event e1 = new Event();
		e1.setName("Evento 1");
		e1.setProcessor("eventLog");
		eventsOnTrue.add(e1);

		rule.setEventsOnTrue(eventsOnTrue);

		final Set<Event> eventsOnFalse = new HashSet<Event>();
		final Event e2 = new Event();
		e2.setName("Evento Alarm");
		e2.setProcessor("eventAlarm");
		eventsOnFalse.add(e2);

		rule.setEventsOnFalse(eventsOnFalse);

		producer.setRules(new LinkedHashSet<>());
		producer.getRules().add(rule);

		rule.setProducer(producer);

		producer = producerRepository.save(producer);

		rule = ruleRepository.save(rule);

	}

	@Test
	public void rules() {

		routeService.loadRoute(producer);

		producerTemplate.sendBody("25;PEDRO FRANCHI;2008;1.85");

		// http://camel.apache.org/spring-testing.html
		// final RuleProcessor ruleProcessor = new RuleProcessor();
		//
		// final Exchange exchange =
		// ExchangeBuilder.anExchange(camelContext).build();
		// final RuleProcessorVO processorVO = new RuleProcessorVO();
		// processorVO.setRule(rule);
		// processorVO.setData(data);
		// exchange.getIn().setBody(processorVO);
		//

		// try {
		// ruleProcessor.process(exchange);
		// } catch (final Exception e) {
		// assert false;
		// e.printStackTrace();
		// }

	}

}
