package br.com.lefranchi.eventz.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.lefranchi.eventz.domain.Nf;

public interface NfRepository extends CrudRepository<Nf, Long> {

	public List<Nf> findAllByOrderByDataDesc();

}
