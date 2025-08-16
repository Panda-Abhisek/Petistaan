package com.abhishekvermaa10.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhishekvermaa10.dto.AverageAgeDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.service.PetService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/petistaan/pets")
public class PetController {

	private final PetService petService;
	
	//option 6
	@GetMapping("/{id}")
	public ResponseEntity<PetDTO> fetchPetDTO(@PathVariable Integer id) throws PetNotFoundException{
		PetDTO petDTO = petService.findPet(id);
		return ResponseEntity.status(HttpStatus.OK).body(petDTO);
	}
	
	//option 7 
	@GetMapping("/avg")
	public ResponseEntity<AverageAgeDTO> findAverageAgeOfPet() {
		AverageAgeDTO averageAgeOfPet = petService.findAverageAgeOfPet();
		return ResponseEntity.status(HttpStatus.OK).body(averageAgeOfPet);
	}
}
