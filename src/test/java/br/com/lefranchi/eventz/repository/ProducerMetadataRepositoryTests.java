package br.com.lefranchi.eventz.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.lefranchi.eventz.Application;
import br.com.lefranchi.eventz.domain.ProducerMetadata;
import br.com.lefranchi.eventz.testutils.ProducerMetadataTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class ProducerMetadataRepositoryTests {

	// TODO: FINALIZAR TESTES.

	@Autowired
	ProducerMetadataRepository repository;
	ProducerMetadata producerMetadata;

	@Before
	public void setUp() {
		producerMetadata = ProducerMetadataTestUtils.newProducerMetadata();
	}

	@Test
	public void findSavedUserById() {

		producerMetadata = repository.save(producerMetadata);

		assertEquals(producerMetadata, repository.findOne(producerMetadata.getId()));

	}

}
