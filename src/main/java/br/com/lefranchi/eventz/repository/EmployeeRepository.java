package br.com.lefranchi.eventz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lefranchi.eventz.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
