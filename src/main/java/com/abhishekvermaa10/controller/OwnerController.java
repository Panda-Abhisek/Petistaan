package com.abhishekvermaa10.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhishekvermaa10.dto.ErrorDTO;
import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import com.abhishekvermaa10.dto.UpdatePetDTO;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.service.OwnerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/petistaan/owners")
@RequiredArgsConstructor
@Tag(name = "Owner", description = "APIs for managing owners and their pets")
public class OwnerController {

	private final OwnerService ownerService;
	
	//option 1
	@Operation(
		    summary = "Create Owner",
		    description = "Save a new owner and their pet to the database."
		)
	@ApiResponse(responseCode = "201", description = "Owner Successfully Created")
	@ApiResponse(responseCode = "400", description = "Constraint Violation Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@PostMapping
	public ResponseEntity<Integer> addOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
		Integer ownerId = ownerService.saveOwner(ownerDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(ownerId);
	}
	
	//option 2
	@Operation(summary = "Get owner by owner ID", description = "Retrieve owner details using their unique id.")
	@ApiResponse(responseCode = "200", description = "Owner Details Retrieved Successfully")
	@ApiResponse(responseCode = "400", description = "Constraint Violation Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "404", description = "Owner Not Found", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@GetMapping("/{id}")
	public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable @Min(value = 1, message = "{owner.id.positive}") Integer id, @RequestHeader(name = "Accept-Language", required = false) String isoCode) throws OwnerNotFoundException{
		OwnerDTO ownerDTO = ownerService.findOwner(id, isoCode);
		return ResponseEntity.status(HttpStatus.OK).body(ownerDTO);
	}
	
	//option 3
	@Operation(summary = "Update owner by owner ID", description = "Update owner details using their unique id.")
	@ApiResponse(responseCode = "200", description = "Owner Details Updated Successfully")
	@ApiResponse(responseCode = "400", description = "Constraint Violation Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "404", description = "Owner Not Found", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@PatchMapping("/{id}")
	public ResponseEntity<UpdatePetDTO> updatePetDetails(@Parameter(description = "Owner id to fetch the owner, must be a positive number.") @PathVariable @Min(value = 1, message = "{owner.id.positive}") Integer id, @Valid @RequestBody UpdatePetDTO newPetDTO) throws OwnerNotFoundException{
		ownerService.updatePetDetails(id, newPetDTO.getName());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	//option 4
	@Operation(summary = "Delete owner by owner ID", description = "Delete owner details using their unique id.")
	@ApiResponse(responseCode = "200", description = "Owner Details Deleted Successfully")
	@ApiResponse(responseCode = "400", description = "Constraint Violation Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "404", description = "Owner Not Found", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOwner(@PathVariable @Min(value = 1, message = "{owner.id.positive}") Integer id) throws OwnerNotFoundException {
		ownerService.deleteOwner(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	//option 5
	@Operation(summary = "Get all owners", description = "Retrieve all owner details.")
	@ApiResponse(responseCode = "200", description = "Owner Details Retrieved Successfully")
	@ApiResponse(responseCode = "400", description = "Constraint Violation Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "404", description = "Owner Not Found", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@GetMapping("/all")
	public ResponseEntity<List<OwnerDTO>> fetchAllOwner() {
		List<OwnerDTO> allOwners = ownerService.findAllOwners();
		return new ResponseEntity<List<OwnerDTO>>(allOwners, HttpStatus.OK);
	}
	
	//option 8
	@Operation(summary = "Get owner details per page", description = "Retrieve details of owners including their IDs, first and last names, and pet names.")
	@ApiResponse(responseCode = "200", description = "Owner Details Retrieved Successfully")
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
	@GetMapping("/page")
	public ResponseEntity<Page<OwnerPetInfoDTO>> ownerPetInfoPagination(@ParameterObject Pageable pageable) {
		Page<OwnerPetInfoDTO> detailsAsPage = ownerService.findOwnerPetDetailsAsPage(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(detailsAsPage);
	}
}
