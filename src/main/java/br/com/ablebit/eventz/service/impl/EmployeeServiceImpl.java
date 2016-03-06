package br.com.ablebit.eventz.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.com.ablebit.eventz.domain.Employee;
import br.com.ablebit.eventz.repository.EmployeeRepository;
import br.com.ablebit.eventz.service.EmployeeService;

@Service
@Validated
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	private final EmployeeRepository repository;

	@Inject
	public EmployeeServiceImpl(final EmployeeRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Employee save(@NotNull @Valid final Employee employee) {
		LOGGER.debug("Creating {}", employee);
		return repository.save(employee);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> getList() {
		LOGGER.debug("Retrieving the list of all users");
		return repository.findAll();
	}

}
