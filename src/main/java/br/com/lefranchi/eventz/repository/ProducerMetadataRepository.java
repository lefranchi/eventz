package br.com.lefranchi.eventz.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lefranchi.eventz.domain.ProducerMetadata;

public interface ProducerMetadataRepository extends CrudRepository<ProducerMetadata, Long> {

}
