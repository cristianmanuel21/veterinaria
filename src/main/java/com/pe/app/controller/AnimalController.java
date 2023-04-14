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
import com.pe.app.model.Animal;
import com.pe.app.repository.AnimalRepository;

@RestController
@RequestMapping("/animal")
public class AnimalController {
	
	@Autowired
	private AnimalRepository animalRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<Animal> getAnimal(@PathVariable(name="id") Long id){
		Optional<Animal> animal = animalRepository.findById(id);
		if(!animal.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(animal.get(),HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<Animal> saveAnimal(@RequestBody Animal animal){
		Animal newAnimal=animalRepository.save(animal);
		return new ResponseEntity<>(newAnimal,HttpStatus.CREATED);
	}

}
