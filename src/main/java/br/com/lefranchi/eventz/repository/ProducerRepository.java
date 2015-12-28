package br.com.lefranchi.eventz.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.lefranchi.eventz.domain.Producer;

public interface ProducerRepository extends CrudRepository<Producer, Long> {

	List<Producer> findByName(String name);

}
