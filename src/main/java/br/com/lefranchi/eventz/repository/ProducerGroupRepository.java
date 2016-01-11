package br.com.lefranchi.eventz.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.lefranchi.eventz.domain.ProducerGroup;

public interface ProducerGroupRepository extends CrudRepository<ProducerGroup, Long> {

	List<ProducerGroup> findByName(String name);

}
