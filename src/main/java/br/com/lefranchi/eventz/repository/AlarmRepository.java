package br.com.lefranchi.eventz.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lefranchi.eventz.domain.Alarm;

public interface AlarmRepository extends CrudRepository<Alarm, Long> {

}
