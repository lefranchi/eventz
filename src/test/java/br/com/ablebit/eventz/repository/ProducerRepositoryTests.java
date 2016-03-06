package br.com.ablebit.eventz.repository;

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

import br.com.ablebit.eventz.Application;
import br.com.ablebit.eventz.domain.Producer;
import br.com.ablebit.eventz.testutils.ProducerMetadataTestUtils;
import br.com.ablebit.eventz.testutils.ProducerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class ProducerRepositoryTests {

	// TODO: FINALIZAR TESTES

	@Autowired
	ProducerRepository repository;
	Producer producer;

	@Autowired
	ProducerMetadataRepository producerMetadataRepository;

	@Before
	public void setUp() {
		producer = ProducerTestUtils.newProducer();
		producer.setMetadata(producerMetadataRepository.save(ProducerMetadataTestUtils.newProducerMetadata()));
	}

	@Test
	public void findSavedUserById() {

		producer = repository.save(producer);

		assertEquals(producer, repository.findOne(producer.getId()));

		assertEquals(producer.getName(), ProducerTestUtils.PRODUCER_NAME);

	}

	@Test
	public void findSavedProducerByName() throws Exception {

		producer = repository.save(producer);

		final List<Producer> producers = repository.findByName(ProducerTestUtils.PRODUCER_NAME);

		assertNotNull(producer);
		assertTrue(producers.contains(producer));

	}

	@Test
	public void delete() throws Exception {

		producer = repository.save(producer);

		List<Producer> producers = repository.findByName(ProducerTestUtils.PRODUCER_NAME);

		assertNotNull(producer);
		assertTrue(producers.contains(producer));

		repository.delete(producer);

		producers = repository.findByName(ProducerTestUtils.PRODUCER_NAME);

		assertEquals(producers.size(), 0);

	}

}
