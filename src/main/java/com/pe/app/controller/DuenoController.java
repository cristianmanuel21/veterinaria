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
import com.pe.app.model.Dueno;
import com.pe.app.repository.DuenoRepository;

@RestController
@RequestMapping("/dueno")
public class DuenoController {
	
	@Autowired
	private DuenoRepository duenoRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<Dueno> getDueno(@PathVariable(name="id") Long id){
		Optional<Dueno> dueno = duenoRepository.findById(id);
		if(!dueno.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(dueno.get(),HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<Dueno> saveAnimal(@RequestBody Dueno dueno){
		Dueno newDueno=duenoRepository.save(dueno);
		return new ResponseEntity<>(newDueno,HttpStatus.CREATED);
	}

}
