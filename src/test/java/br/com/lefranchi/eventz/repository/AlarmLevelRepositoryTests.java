package br.com.lefranchi.eventz.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.lefranchi.eventz.Application;
import br.com.lefranchi.eventz.domain.AlarmLevel;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class AlarmLevelRepositoryTests {

	// TODO : TESTAR UNIQUE - ALARMLEVEL

	@Autowired
	AlarmLevelRepository repository;
	AlarmLevel alarmLevel;

	@Before
	public void setUp() {

		alarmLevel = new AlarmLevel();
		alarmLevel.setName("Informação");
		alarmLevel.setImage("info.png");
		alarmLevel.setColor("blue");

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmLevelIntegrityAll() {

		alarmLevel = new AlarmLevel();

		alarmLevel = repository.save(alarmLevel);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmLevelIntegrityName() {

		alarmLevel.setName(null);

		alarmLevel = repository.save(alarmLevel);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmLevelIntegrityImage() {

		alarmLevel.setImage(null);

		alarmLevel = repository.save(alarmLevel);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testAlarmLevelIntegrityColor() {

		alarmLevel.setColor(null);

		alarmLevel = repository.save(alarmLevel);

	}

	@Test
	public void findSavedEventById() {

		alarmLevel = repository.save(alarmLevel);

		assertEquals(alarmLevel, repository.findOne(alarmLevel.getId()));

	}

}
