package br.com.lefranchi.eventz.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lefranchi.eventz.domain.Employee;
import br.com.lefranchi.eventz.service.EmployeeService;

@RestController
public class EmployeeController {
	private final EmployeeService employeeService;

	@Inject
	public EmployeeController(final EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public List<Employee> listEmployees() {
		final List<Employee> employees = employeeService.getList();
		return employees;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public void addEmployee(@RequestParam(value = "employeeName", required = false) final String employeeName,
			@RequestParam(value = "employeeId", required = false) final String employeeId,
			@RequestParam(value = "employeeCity", required = false) final String employeeCity) {
		final Employee employee = new Employee(employeeId, employeeName, employeeCity);
		employeeService.save(employee);
	}
}
