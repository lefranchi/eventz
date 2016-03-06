package br.com.ablebit.eventz.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.ablebit.eventz.domain.Alarm;

public interface AlarmRepository extends CrudRepository<Alarm, Long> {

}
