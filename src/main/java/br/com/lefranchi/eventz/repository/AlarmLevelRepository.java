package br.com.lefranchi.eventz.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lefranchi.eventz.domain.AlarmLevel;

public interface AlarmLevelRepository extends CrudRepository<AlarmLevel, Long> {

}
