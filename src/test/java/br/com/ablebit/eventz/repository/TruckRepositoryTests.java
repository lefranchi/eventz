package br.com.ablebit.eventz.repository;

import static org.junit.Assert.assertEquals;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.ablebit.eventz.Application;
import br.com.ablebit.eventz.domain.Truck;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class TruckRepositoryTests {

	@Autowired
	TruckRepository repository;
	Truck truck;

	@Before
	public void setUp() {
		truck = new Truck();
		truck.setIdentification("C1");
		truck.setPlate("DER-4566");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testTruckIntegrityAll() {

		Truck truck = new Truck();

		truck = repository.save(truck);

	}

	@Test(expected = ConstraintViolationException.class)
	public void testTruckIntegrityIdentification() {

		Truck truck = new Truck();
		truck.setPlate("DER-4566");

		truck = repository.save(truck);

	}

	@Test(expected = ConstraintViolationException.class)
	public void testTruckIntegrityPlate() {

		Truck truck = new Truck();
		truck.setIdentification("C1");

		truck = repository.save(truck);

	}

	@Test
	public void findSavedTruckById() {

		truck = repository.save(truck);

		assertEquals(truck, repository.findOne(truck.getId()));
	}

}
