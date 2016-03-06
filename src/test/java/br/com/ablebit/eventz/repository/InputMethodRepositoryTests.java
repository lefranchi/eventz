package br.com.ablebit.eventz.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.ablebit.eventz.Application;
import br.com.ablebit.eventz.domain.InputMethod;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
public class InputMethodRepositoryTests {

	// TODO: FINALIZAR TESTES

	private static final String INPUT_METHOD_NAME = "File";

	@Autowired
	InputMethodRepository repository;
	InputMethod inputMethod;

	@Before
	public void setUp() {
		inputMethod = new InputMethod();
		inputMethod.setName(INPUT_METHOD_NAME);
		inputMethod.setComponentName("file");
		inputMethod.setDescription("Importa arquivo.");
		inputMethod.setProperties(new HashSet<>());
		inputMethod.getProperties().add("directoryName");
		inputMethod.getProperties().add("noop");
	}

	@Test
	public void findSavedUserById() {

		inputMethod = repository.save(inputMethod);

		assertEquals(inputMethod, repository.findOne(inputMethod.getId()));

		assertEquals(inputMethod.getName(), INPUT_METHOD_NAME);

	}

	@Test
	public void findSavedProducerByName() throws Exception {

		inputMethod = repository.save(inputMethod);

		final List<InputMethod> inputMethods = repository.findByName(INPUT_METHOD_NAME);

		assertNotNull(inputMethod);
		assertTrue(inputMethods.contains(inputMethod));

	}

	@Test
	public void delete() throws Exception {

		inputMethod = repository.save(inputMethod);

		List<InputMethod> inputMethods = repository.findByName(INPUT_METHOD_NAME);

		assertNotNull(inputMethod);
		assertTrue(inputMethods.contains(inputMethod));

		repository.delete(inputMethod);

		inputMethods = repository.findByName(INPUT_METHOD_NAME);

		assertEquals(inputMethods.size(), 0);

	}

}
