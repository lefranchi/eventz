package br.com.lefranchi.eventz.service;

import java.util.List;

import br.com.lefranchi.eventz.domain.Employee;

public interface EmployeeService {

	Employee save(Employee employee);

	List<Employee> getList();

}
