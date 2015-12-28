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
import br.com.lefranchi.eventz.domain.Event;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class EventRepositoryTests {

	@Autowired
	EventRepository repository;
	Event event;

	@Before
	public void setUp() {
		event = new Event();
		event.setName("Evento de Log");
		event.setProcessor("br.com.lefranchi.events.event.EventLog");
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testEventIntegrityAll() {

		Event event = new Event();

		event = repository.save(event);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testEventIntegrityName() {

		Event event = new Event();
		event.setProcessor("br.com.lefranchi.events.event.EventLog");

		event = repository.save(event);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testEventIntegrityProcessor() {

		Event event = new Event();
		event.setName("Event Test");

		event = repository.save(event);

	}

	@Test
	public void findSavedEventById() {

		event = repository.save(event);

		assertEquals(event, repository.findOne(event.getId()));
	}

	@Test
	public void findSavedEventByName() throws Exception {

		event = repository.save(event);

		final List<Event> events = repository.findByName("Evento de Log");

		assertNotNull(events);
		assertTrue(events.contains(event));

	}

}
