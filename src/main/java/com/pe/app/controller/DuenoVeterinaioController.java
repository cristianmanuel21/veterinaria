package com.pe.app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pe.app.model.Dueno;
import com.pe.app.model.DuenoVeterinaria;
import com.pe.app.model.Veterinaria;
import com.pe.app.repository.DuenoRepository;
import com.pe.app.repository.DuenoVeterinariaRepository;
import com.pe.app.repository.VeterinariaRepository;

@RestController
@RequestMapping("/duenoveterinaria")
public class DuenoVeterinaioController {
	
	@Autowired
	private VeterinariaRepository veterinariaRepository;
	
	@Autowired
	private DuenoVeterinariaRepository duenoVeterinariaRepository;
	
	@Autowired
	private DuenoRepository duenoRepository;
	
	
	@PostMapping("/dueno/{duenoId}/veterinaria/{veterinariaId}")
	public ResponseEntity<DuenoVeterinaria> guardar(@PathVariable(name="duenoId") Long duenoId,@PathVariable(name="veterinariaId") Long veterinariaId){
		
		Optional<Dueno> dueno=duenoRepository.findById(duenoId);
		Optional<Veterinaria> veterinaria=veterinariaRepository.findById(veterinariaId);
		
		if(!dueno.isPresent() && !veterinaria.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		DuenoVeterinaria duenoVeterinaria=new DuenoVeterinaria();
		duenoVeterinaria.setDueno(dueno.get());
		duenoVeterinaria.setVeterinaria(veterinaria.get());
		duenoVeterinaria.setCreated_at(LocalDate.now());
		duenoVeterinaria=duenoVeterinariaRepository.save(duenoVeterinaria);
		
		return new ResponseEntity<>(duenoVeterinaria,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/email/veterinaria/{veterinariaName}")
	public ResponseEntity<?> enviarCorreo(@PathVariable(name="veterinariaName") String veterinariaName){
		Veterinaria vet=veterinariaRepository.findByNombre(veterinariaName);
		if(vet==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<Long> duenosId=duenoVeterinariaRepository.findDuenosBytVetId(vet.getId());
		if(duenosId.size()<1) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<Dueno> duenos = (List<Dueno>) duenoRepository.findAllById(duenosId);
		for(Dueno dueno: duenos) {
			enviarEmail(dueno);
		}
		return ResponseEntity.ok(duenos);	
	} 
	
	private String enviarEmail(Dueno dueno) {
		System.out.println("Enviado correo a: "+dueno.getCorreo());
		return "Enviado correo a: "+dueno.getCorreo();
	}
	
	
}
