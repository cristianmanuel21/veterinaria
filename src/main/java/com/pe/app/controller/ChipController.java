package com.pe.app.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pe.app.exception.ResourceNotFoundException;
import com.pe.app.model.Chip;
import com.pe.app.repository.ChipRepository;

@RestController
@RequestMapping("/chip")
public class ChipController {

	@Autowired
	private ChipRepository chipRepository;

	@GetMapping("/{id}")
	public ResponseEntity<Chip> getChip(@PathVariable(name = "id") Long id) {
		Optional<Chip> chip = chipRepository.findById(id);
		if (!chip.isPresent()) {
			// return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			throw new ResourceNotFoundException("Chip with id " + id + " not found");
		}
		return new ResponseEntity<>(chip.get(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Chip> saveChip(@Valid @RequestBody Chip chip) {
		Chip newCHip = chipRepository.save(chip);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newCacheUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCHip.getId())
				.toUri();
		responseHeaders.setLocation(newCacheUri);
		return new ResponseEntity<>(newCHip, responseHeaders, HttpStatus.CREATED);
		// return new ResponseEntity<>(null,HttpStatus.CREATED);
	}

}
