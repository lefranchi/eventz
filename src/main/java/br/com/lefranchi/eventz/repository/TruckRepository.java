package br.com.lefranchi.eventz.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lefranchi.eventz.domain.Truck;

public interface TruckRepository extends CrudRepository<Truck, Long> {

}
