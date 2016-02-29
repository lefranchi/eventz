package br.com.lefranchi.eventz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.lefranchi.eventz.domain.Nf;

@RepositoryRestResource(collectionResourceRel = "nf", path = "nf")
public interface NfRepository extends CrudRepository<Nf, Long> {

}
