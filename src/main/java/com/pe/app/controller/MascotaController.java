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
import com.pe.app.model.Chip;
import com.pe.app.model.Dueno;
import com.pe.app.model.Mascota;
import com.pe.app.repository.AnimalRepository;
import com.pe.app.repository.ChipRepository;
import com.pe.app.repository.DuenoRepository;
import com.pe.app.repository.MascotaRepository;

@RestController
@RequestMapping("/mascota")
public class MascotaController {
	
	
	@Autowired
	private MascotaRepository mascotaRepository;
	
	@Autowired
	private ChipRepository chipRepository;
	
	@Autowired
	private DuenoRepository duenoRepository;
	
	@Autowired
	private AnimalRepository animalRepository;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Mascota> getMascota(@PathVariable(name="id") Long id){
		Optional<Mascota> mascota = mascotaRepository.findById(id);
		if(!mascota.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(mascota.get(),HttpStatus.OK);
	}
	
	
	@PostMapping("/animal/{animalId}/chip/{chipId}/dueno/{duenoId}")
	public ResponseEntity<Mascota> saveChip(@RequestBody Mascota mascota,@PathVariable("animalId") Long animalId,
			@PathVariable("chipId") Long chipId,@PathVariable("duenoId") Long duenoId){
		Optional<Animal> animalOp=animalRepository.findById(animalId);
		Optional<Chip> chipOp=chipRepository.findById(chipId);
		Optional<Dueno> duenoOp=duenoRepository.findById(duenoId);
		if(animalOp.isEmpty() && chipOp.isEmpty() && duenoOp.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Mascota newMascota;
		mascota.setAnimal(animalOp.get());
		mascota.setChip(chipOp.get());
		mascota.setDueno(duenoOp.get());
		newMascota=mascotaRepository.save(mascota);
		
		return new ResponseEntity<>(newMascota,HttpStatus.CREATED);
	}
	
	
	
	

}
