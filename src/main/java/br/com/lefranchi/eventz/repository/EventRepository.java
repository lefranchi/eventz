package br.com.lefranchi.eventz.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.lefranchi.eventz.domain.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

	List<Event> findByName(String name);

}
