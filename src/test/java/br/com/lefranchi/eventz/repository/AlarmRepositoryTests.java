package br.com.lefranchi.eventz.repository;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.lefranchi.eventz.Application;
import br.com.lefranchi.eventz.domain.Alarm;
import br.com.lefranchi.eventz.domain.FormulaType;
import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.domain.ProducerData;
import br.com.lefranchi.eventz.testutils.ProducerDataTestUtils;
import br.com.lefranchi.eventz.testutils.ProducerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class AlarmRepositoryTests {

	@Autowired
	ProducerRepository producerRepository;
	Producer producer;

	@Autowired
	ProducerDataRepository producerDataRepository;
	ProducerData producerData;

	@Autowired
	AlarmRepository repository;
	Alarm alarm;

	@Before
	public void setUp() {

		producer = producerRepository.save(ProducerTestUtils.newProducer());

		producerData = producerDataRepository.save(ProducerDataTestUtils.newProducerData(producer));

		alarm = new Alarm();
		alarm.setProducerData(producerData);
		alarm.setDate(Calendar.getInstance());
		alarm.setFormula("iadade > 10");
		alarm.setType(FormulaType.JEXL);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityAll() {

		Alarm alarm = new Alarm();

		alarm = repository.save(alarm);
		alarm.setProducerData(null);
		alarm.setDate(null);
		alarm.setFormula(null);
		alarm.setType(null);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityProducerData() {

		Alarm alarm = new Alarm();

		alarm = repository.save(alarm);
		alarm.setProducerData(null);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityDate() {

		Alarm alarm = new Alarm();

		alarm = repository.save(alarm);
		alarm.setDate(null);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityFormula() {

		Alarm alarm = new Alarm();

		alarm = repository.save(alarm);
		alarm.setFormula(null);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityType() {

		Alarm alarm = new Alarm();

		alarm = repository.save(alarm);
		alarm.setType(null);

	}

	@Test
	public void findSavedEventById() {

		alarm = repository.save(alarm);

		assertEquals(alarm, repository.findOne(alarm.getId()));

	}

}
