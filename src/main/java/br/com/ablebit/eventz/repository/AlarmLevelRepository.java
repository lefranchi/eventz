package br.com.ablebit.eventz.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.ablebit.eventz.domain.AlarmLevel;

public interface AlarmLevelRepository extends CrudRepository<AlarmLevel, Long> {

}
