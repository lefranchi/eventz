package br.com.lefranchi.eventz.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.lefranchi.eventz.Application;
import br.com.lefranchi.eventz.domain.Producer;
import br.com.lefranchi.eventz.repository.ProducerRepository;
import br.com.lefranchi.eventz.testutils.ProducerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class RouteServiceTest {

	@Autowired
	ProducerRepository repository;
	Producer producer;

	@Autowired
	RouteService routeService;

	@Before
	public void setUp() {
		producer = ProducerTestUtils.newProducer();
		repository.save(producer);
	}

	@Test
	public void loadRoutesTest() {

		routeService.loadRoutes();

	}
}
