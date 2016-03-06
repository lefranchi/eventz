package br.com.ablebit.eventz.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.ablebit.eventz.domain.ProducerData;

public interface ProducerDataRepository extends CrudRepository<ProducerData, Long> {

}
