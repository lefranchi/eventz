package br.com.ablebit.eventz.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.ablebit.eventz.domain.Rule;

public interface RuleRepository extends CrudRepository<Rule, Long> {

	List<Rule> findByName(String name);

}
