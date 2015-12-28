package br.com.lefranchi.eventz.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.lefranchi.eventz.Application;
import br.com.lefranchi.eventz.domain.FormulaType;
import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.domain.Rule;
import br.com.lefranchi.eventz.testutils.ProducerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class RuleRepositoryTests {

	@Autowired
	ProducerRepository producerRepository;
	Producer producer;

	@Autowired
	RuleRepository repository;
	Rule rule;

	String ruleName = "Rule 1";

	@Before
	public void setUp() {

		producer = producerRepository.save(ProducerTestUtils.newProducer());

		rule = new Rule();
		rule.setName(ruleName);
		rule.setType(FormulaType.JEXL);
		rule.setFormula("idade > 10");
		rule.setProducer(producer);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testEventIntegrityAll() {

		final Rule rule = new Rule();

		repository.save(rule);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testEventIntegrityName() {

		final Rule rule = new Rule();
		rule.setFormula(" ");
		rule.setType(FormulaType.EL);
		rule.setProducer(ProducerTestUtils.newProducer());

		repository.save(rule);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testEventIntegrityFormula() {

		final Rule rule = new Rule();
		rule.setName("Name");
		rule.setType(FormulaType.EL);
		rule.setProducer(ProducerTestUtils.newProducer());

		repository.save(rule);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testEventIntegrityFormulaType() {

		final Rule rule = new Rule();
		rule.setName("Name");
		rule.setFormula(" ");
		rule.setProducer(ProducerTestUtils.newProducer());

		repository.save(rule);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testEventIntegrityWhenProducer() {

		final Rule rule = new Rule();
		rule.setName("Name");
		rule.setFormula(" ");

		repository.save(rule);

	}

	@Test
	public void findSavedRuleById() {

		rule = repository.save(rule);

		assertEquals(rule, repository.findOne(rule.getId()));

	}

	@Test
	public void findSavedRuleByName() throws Exception {

		rule = repository.save(rule);

		final List<Rule> rules = repository.findByName(ruleName);

		assertNotNull(rules);
		assertTrue(rules.contains(rule));

	}

}
