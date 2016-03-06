package br.com.ablebit.eventz.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.ablebit.eventz.domain.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

	List<Event> findByName(String name);

}
