package br.com.ablebit.eventz.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.ablebit.eventz.domain.Producer;

public interface ProducerRepository extends CrudRepository<Producer, Long> {

	List<Producer> findByName(String name);

}
