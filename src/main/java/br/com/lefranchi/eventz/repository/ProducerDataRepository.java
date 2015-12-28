package br.com.lefranchi.eventz.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lefranchi.eventz.domain.ProducerData;

public interface ProducerDataRepository extends CrudRepository<ProducerData, Long> {

}
