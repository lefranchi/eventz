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
import br.com.lefranchi.eventz.domain.AlarmLevel;
import br.com.lefranchi.eventz.domain.FormulaType;
import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.testutils.AlarmLevelTestUtils;
import br.com.lefranchi.eventz.testutils.ProducerMetadataTestUtils;
import br.com.lefranchi.eventz.testutils.ProducerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class AlarmRepositoryTests {

	@Autowired
	ProducerRepository producerRepository;
	Producer producer;

	@Autowired
	ProducerMetadataRepository producerMetadataRepository;

	@Autowired
	AlarmLevelRepository alarmLevelRepository;
	AlarmLevel alarmLevel;

	@Autowired
	AlarmRepository repository;
	Alarm alarm;

	@Before
	public void setUp() {

		producer = ProducerTestUtils.newProducer();
		producer.setMetadata(producerMetadataRepository.save(ProducerMetadataTestUtils.newProducerMetadata()));
		producer = producerRepository.save(producer);

		alarmLevel = alarmLevelRepository.save(AlarmLevelTestUtils.newAlarmLevel());

		alarm = new Alarm();
		alarm.setDescription("Description");
		alarm.setDate(Calendar.getInstance());
		alarm.setFormula("iadade > 10");
		alarm.setType(FormulaType.JEXL);
		alarm.setLevel(alarmLevel);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityAll() {

		Alarm alarm = new Alarm();

		alarm = repository.save(alarm);
		alarm.setDescription(null);
		alarm.setDate(null);
		alarm.setFormula(null);
		alarm.setType(null);
		alarm.setLevel(null);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityDescription() {

		alarm.setDescription(null);

		alarm = repository.save(alarm);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityDate() {

		alarm.setDate(null);

		alarm = repository.save(alarm);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityFormula() {

		alarm.setFormula(null);

		alarm = repository.save(alarm);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityType() {

		alarm.setType(null);

		alarm = repository.save(alarm);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmIntegrityLevel() {

		alarm.setLevel(null);

		alarm = repository.save(alarm);

	}

	@Test
	public void findSavedEventById() {

		alarm = repository.save(alarm);

		assertEquals(alarm, repository.findOne(alarm.getId()));

	}

}
