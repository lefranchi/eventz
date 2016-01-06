package br.com.lefranchi.eventz.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.lefranchi.eventz.Application;
import br.com.lefranchi.eventz.domain.ProducerGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class ProducerGroupRepositoryTests {

	// TODO: FINALIZAR TESTES

	private static final String PRODUCER_GROUP_NAME = "Producer Group";

	@Autowired
	ProducerGroupRepository repository;
	ProducerGroup producerGroup;

	@Before
	public void setUp() {
		producerGroup = new ProducerGroup();
		producerGroup.setName(PRODUCER_GROUP_NAME);
	}

	@Test
	public void findSavedUserById() {

		producerGroup = repository.save(producerGroup);

		assertEquals(producerGroup, repository.findOne(producerGroup.getId()));

		assertEquals(producerGroup.getName(), PRODUCER_GROUP_NAME);

	}

	@Test
	public void findSavedProducerByName() throws Exception {

		producerGroup = repository.save(producerGroup);

		final List<ProducerGroup> producerGroups = repository.findByName(PRODUCER_GROUP_NAME);

		assertNotNull(producerGroup);
		assertTrue(producerGroups.contains(producerGroup));

	}

	@Test
	public void delete() throws Exception {

		producerGroup = repository.save(producerGroup);

		List<ProducerGroup> producerGroups = repository.findByName(PRODUCER_GROUP_NAME);

		assertNotNull(producerGroup);
		assertTrue(producerGroups.contains(producerGroup));

		repository.delete(producerGroup);

		producerGroups = repository.findByName(PRODUCER_GROUP_NAME);

		assertEquals(producerGroups.size(), 0);

	}

}
