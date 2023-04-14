package com.pe.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.pe.app.model.Animal;

public interface AnimalRepository extends CrudRepository<Animal, Long>{

}
