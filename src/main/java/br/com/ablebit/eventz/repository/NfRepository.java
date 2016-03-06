package br.com.ablebit.eventz.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.ablebit.eventz.domain.Nf;

public interface NfRepository extends CrudRepository<Nf, Long> {

	public List<Nf> findAllByOrderByDataDesc();

}
