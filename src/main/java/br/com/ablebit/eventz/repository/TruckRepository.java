package br.com.ablebit.eventz.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.ablebit.eventz.domain.Truck;

public interface TruckRepository extends CrudRepository<Truck, Long> {

}
