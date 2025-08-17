package com.abhishekvermaa10.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import com.abhishekvermaa10.dto.UpdatePetDTO;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.service.OwnerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/petistaan/owners")
@RequiredArgsConstructor
public class OwnerController {

	private final OwnerService ownerService;
	
	//option 1
	@PostMapping
	public ResponseEntity<Integer> addOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
		Integer ownerId = ownerService.saveOwner(ownerDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(ownerId);
	}
	
	//option 2
	@GetMapping("/{id}")
	public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable @Min(value = 1, message = "{owner.id.positive}") Integer id) throws OwnerNotFoundException{
		OwnerDTO ownerDTO = ownerService.findOwner(id);
		return ResponseEntity.status(HttpStatus.OK).body(ownerDTO);
	}
	
	//option 3
	@PatchMapping("/{id}")
	public ResponseEntity<UpdatePetDTO> updatePetDetails(@PathVariable @Min(value = 1, message = "{owner.id.positive}") Integer id, @Valid @RequestBody UpdatePetDTO newPetDTO) throws OwnerNotFoundException{
		ownerService.updatePetDetails(id, newPetDTO.getName());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	//option 4
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOwner(@PathVariable @Min(value = 1, message = "{owner.id.positive}") Integer id) throws OwnerNotFoundException {
		ownerService.deleteOwner(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	//option 5
	@GetMapping("/all")
	public ResponseEntity<List<OwnerDTO>> fetchAllOwner() {
		List<OwnerDTO> allOwners = ownerService.findAllOwners();
		return new ResponseEntity<List<OwnerDTO>>(allOwners, HttpStatus.OK);
	}
	
	//option 8
	@GetMapping("/page")
	public ResponseEntity<Page<OwnerPetInfoDTO>> ownerPetInfoPagination(Pageable pageable) {
		Page<OwnerPetInfoDTO> detailsAsPage = ownerService.findOwnerPetDetailsAsPage(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(detailsAsPage);
	}
}
