package br.com.lefranchi.eventz.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.lefranchi.eventz.Application;
import br.com.lefranchi.eventz.domain.InputMethod;
import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.domain.ProducerInputMethod;
import br.com.lefranchi.eventz.repository.InputMethodRepository;
import br.com.lefranchi.eventz.repository.ProducerMetadataRepository;
import br.com.lefranchi.eventz.repository.ProducerRepository;
import br.com.lefranchi.eventz.testutils.ProducerMetadataTestUtils;
import br.com.lefranchi.eventz.testutils.ProducerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class RouteServiceTest {

	// TODO FINALIZAR TESTES

	@Autowired
	ProducerRepository repository;
	Producer producer;

	@Autowired
	ProducerMetadataRepository producerMetadataRepository;

	@Autowired
	InputMethodRepository inputMethodRepository;
	InputMethod inputMethod;

	@Autowired
	RouteService routeService;

	@Before
	public void setUp() {

		InputMethod inputMethod = new InputMethod();
		inputMethod.setName("Direct");
		inputMethod.setComponentName("direct:start");

		inputMethod = inputMethodRepository.save(inputMethod);

		final ProducerInputMethod producerInputMethod = new ProducerInputMethod();
		producerInputMethod.setInputMethod(inputMethod);

		producer = ProducerTestUtils.newProducer();
		producer.setInputMethod(producerInputMethod);
		producer.setMetadata(producerMetadataRepository.save(ProducerMetadataTestUtils.newProducerMetadata()));

		repository.save(producer);
	}

	@Test
	public void loadRoutesTest() {

		routeService.loadRoutes();

	}
}
