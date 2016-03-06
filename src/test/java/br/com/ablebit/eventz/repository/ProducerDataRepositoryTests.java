package br.com.ablebit.eventz.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.ablebit.eventz.Application;
import br.com.ablebit.eventz.domain.Producer;
import br.com.ablebit.eventz.domain.ProducerData;
import br.com.ablebit.eventz.testutils.ProducerDataTestUtils;
import br.com.ablebit.eventz.testutils.ProducerMetadataTestUtils;
import br.com.ablebit.eventz.testutils.ProducerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class ProducerDataRepositoryTests {

	@Autowired
	ProducerRepository producerRepository;
	Producer producer;

	@Autowired
	ProducerMetadataRepository producerMetadataRepository;

	@Autowired
	ProducerDataRepository repository;
	ProducerData producerData;

	@Before
	public void setUp() {

		producer = ProducerTestUtils.newProducer();
		producer.setMetadata(producerMetadataRepository.save(ProducerMetadataTestUtils.newProducerMetadata()));
		producer = producerRepository.save(producer);

		producerData = ProducerDataTestUtils.newProducerData(producer);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testProducerDataIntegrityAll() {

		final ProducerData producerData = new ProducerData();

		repository.save(producerData);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testProducerDataIntegrityProducer() {

		producerData.setProducer(null);

		repository.save(producerData);

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testProducerDataIntegrityData() {

		producerData.setData(null);

		repository.save(producerData);

	}

	@Test
	public void findSavedProducerDataById() {

		producerData = repository.save(producerData);

		assertEquals(producerData, repository.findOne(producerData.getId()));

	}

}
