package br.com.ablebit.eventz.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.ablebit.eventz.domain.InputMethod;

public interface InputMethodRepository extends CrudRepository<InputMethod, Long> {

	List<InputMethod> findByName(String name);

}
