package br.com.lefranchi.eventz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.lefranchi.eventz.domain.Caminhao;

@RepositoryRestResource(collectionResourceRel = "caminhao", path = "caminhao")
public interface CaminhaoRepository extends CrudRepository<Caminhao, Long> {

}
