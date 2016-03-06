package br.com.ablebit.eventz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ablebit.eventz.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
