package com.pe.app.repository;

import org.springframework.data.repository.CrudRepository;
import com.pe.app.model.Veterinaria;

public interface VeterinariaRepository extends CrudRepository<Veterinaria, Long>{
	
	public Veterinaria findByNombre(String nombre);

}
