package br.com.ablebit.eventz.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.ablebit.eventz.domain.ProducerMetadata;

public interface ProducerMetadataRepository extends CrudRepository<ProducerMetadata, Long> {

}
