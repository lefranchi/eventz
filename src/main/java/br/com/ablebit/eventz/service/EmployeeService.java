package br.com.ablebit.eventz.service;

import java.util.List;

import br.com.ablebit.eventz.domain.Employee;

public interface EmployeeService {

	Employee save(Employee employee);

	List<Employee> getList();

}
