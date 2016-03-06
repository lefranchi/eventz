package br.com.ablebit.eventz.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.ablebit.eventz.Application;
import br.com.ablebit.eventz.domain.InputMethod;
import br.com.ablebit.eventz.domain.Producer;
import br.com.ablebit.eventz.domain.ProducerInputMethod;
import br.com.ablebit.eventz.repository.InputMethodRepository;
import br.com.ablebit.eventz.repository.ProducerMetadataRepository;
import br.com.ablebit.eventz.repository.ProducerRepository;
import br.com.ablebit.eventz.testutils.ProducerMetadataTestUtils;
import br.com.ablebit.eventz.testutils.ProducerTestUtils;

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
