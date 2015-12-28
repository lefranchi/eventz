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
import br.com.lefranchi.eventz.domain.DataFieldMetadata;
import br.com.lefranchi.eventz.domain.FieldDataType;
import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.domain.ProducerDataType;
import br.com.lefranchi.eventz.testutils.ProducerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class ProducerRepositoryTests {

	@Autowired
	ProducerRepository repository;
	Producer producer;

	@Before
	public void setUp() {
		producer = ProducerTestUtils.newProducer();
	}

	@Test
	public void findSavedUserById() {

		producer = repository.save(producer);

		assertEquals(producer, repository.findOne(producer.getId()));

		assertEquals(producer.getName(), ProducerTestUtils.PRODUCER_NAME);

		assertNotNull(producer.getMetadata());

		assertEquals(producer.getMetadata().getSampleData(), ProducerTestUtils.PRODUCER_SAMPLE_DATA);

		assertEquals(producer.getMetadata().getDataType(), ProducerDataType.DELIMITED);

		assertEquals(producer.getMetadata().getFields().size(), 5);

		int c = 0;
		for (final DataFieldMetadata fieldMetadata : producer.getMetadata().getFields()) {

			switch (c) {
			case 0:
				assertEquals(fieldMetadata.getName(), "id");
				assertEquals(fieldMetadata.getType(), FieldDataType.NUMBER);
				break;
			case 1:
				assertEquals(fieldMetadata.getName(), "name");
				assertEquals(fieldMetadata.getType(), FieldDataType.STRING);
				break;
			case 2:
				assertEquals(fieldMetadata.getName(), "year");
				assertEquals(fieldMetadata.getType(), FieldDataType.NUMBER);
				break;
			case 3:
				assertEquals(fieldMetadata.getName(), "active");
				assertEquals(fieldMetadata.getType(), FieldDataType.BOOLEAN);
				break;
			case 4:
				assertEquals(fieldMetadata.getName(), "content");
				assertEquals(fieldMetadata.getType(), FieldDataType.NUMBER);
				break;
			}

			c++;

		}

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

	@Test
	public void update() throws Exception {

		producer = repository.save(producer);

		final List<Producer> producers = repository.findByName(ProducerTestUtils.PRODUCER_NAME);

		assertNotNull(producer);
		assertTrue(producers.contains(producer));

		producer.setName("New Name");
		producer.getMetadata().setSampleData("New Sample Data");

		final DataFieldMetadata fieldNew = new DataFieldMetadata();
		fieldNew.setName("new");
		fieldNew.setType(FieldDataType.NUMBER);
		fieldNew.setOrder(6);
		fieldNew.setProducerMetadata(producer.getMetadata());
		producer.getMetadata().getFields().add(fieldNew);

		producer.getMetadata().getFields().stream().findFirst().get().setName("New Id");

		producer = repository.save(producer);

		assertEquals(producer.getName(), "New Name");

		assertNotNull(producer.getMetadata());

		assertEquals(producer.getMetadata().getSampleData(), "New Sample Data");

		assertEquals(producer.getMetadata().getDataType(), ProducerDataType.DELIMITED);

		assertEquals(producer.getMetadata().getFields().size(), 6);

		int c = 0;
		for (final DataFieldMetadata fieldMetadata : producer.getMetadata().getFields()) {

			switch (c) {
			case 0:
				assertEquals(fieldMetadata.getName(), "New Id");
				assertEquals(fieldMetadata.getType(), FieldDataType.NUMBER);
				break;
			case 1:
				assertEquals(fieldMetadata.getName(), "name");
				assertEquals(fieldMetadata.getType(), FieldDataType.STRING);
				break;
			case 2:
				assertEquals(fieldMetadata.getName(), "year");
				assertEquals(fieldMetadata.getType(), FieldDataType.NUMBER);
				break;
			case 3:
				assertEquals(fieldMetadata.getName(), "active");
				assertEquals(fieldMetadata.getType(), FieldDataType.BOOLEAN);
				break;
			case 4:
				assertEquals(fieldMetadata.getName(), "content");
				assertEquals(fieldMetadata.getType(), FieldDataType.NUMBER);
				break;
			case 5:
				assertEquals(fieldMetadata.getName(), "new");
				assertEquals(fieldMetadata.getType(), FieldDataType.NUMBER);
				break;
			}

			c++;

		}

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void duplicatedMetadataField() throws Exception {

		final DataFieldMetadata fieldNew = new DataFieldMetadata();
		fieldNew.setName("new");
		fieldNew.setType(FieldDataType.NUMBER);
		fieldNew.setOrder(3);
		fieldNew.setProducerMetadata(producer.getMetadata());
		producer.getMetadata().getFields().add(fieldNew);

		producer = repository.save(producer);

	}
}
