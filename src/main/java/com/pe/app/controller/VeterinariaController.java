package com.pe.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pe.app.model.Veterinaria;
import com.pe.app.repository.VeterinariaRepository;

@RestController
@RequestMapping("/veterinaria")
public class VeterinariaController {
	
	@Autowired
	private VeterinariaRepository veterinariaRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<Veterinaria> getDueno(@PathVariable(name="id") Long id){
		Optional<Veterinaria> veterinaria = veterinariaRepository.findById(id);
		if(!veterinaria.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(veterinaria.get(),HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<Veterinaria> saveAnimal(@RequestBody Veterinaria veterinaria){
		Veterinaria newVeterinaria=veterinariaRepository.save(veterinaria);
		return new ResponseEntity<>(newVeterinaria,HttpStatus.CREATED);
	}


}
