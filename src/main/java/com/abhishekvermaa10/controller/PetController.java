package com.abhishekvermaa10.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhishekvermaa10.dto.AverageAgeDTO;
import com.abhishekvermaa10.dto.ErrorDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.service.PetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/petistaan/pets")
@Tag(name = "Pet", description = "APIs for managing pets")

public class PetController {

	private final PetService petService;
	
	//option 6
	@Operation(summary = "Get pet by pet ID", description = "Retrieve pet details using their unique id.")
	@ApiResponse(responseCode = "200", description = "Pet Details Retrieved Successfully")
	@ApiResponse(responseCode = "400", description = "Constraint Violation Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "404", description = "Owner Not Found", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@GetMapping("/{id}")
	public ResponseEntity<PetDTO> fetchPetDTO(@PathVariable @Min(value = 1, message = "{pet.id.positive}") Integer id) throws PetNotFoundException{
		PetDTO petDTO = petService.findPet(id);
		return ResponseEntity.status(HttpStatus.OK).body(petDTO);
	}
	
	//option 7 
	@Operation(summary = "Get Average Age of Pet", description = "Retrieve average age of the pets.")
	@ApiResponse(responseCode = "200", description = "Pet average Age Retrieved Successfully")
	@ApiResponse(responseCode = "400", description = "Constraint Violation Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "404", description = "Owner Not Found", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@GetMapping("/avg")
	public ResponseEntity<AverageAgeDTO> findAverageAgeOfPet() {
		AverageAgeDTO averageAgeOfPet = petService.findAverageAgeOfPet();
		return ResponseEntity.status(HttpStatus.OK).body(averageAgeOfPet);
	}
}
