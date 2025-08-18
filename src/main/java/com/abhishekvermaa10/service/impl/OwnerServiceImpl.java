package com.abhishekvermaa10.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abhishekvermaa10.dto.DomesticPetDTO;
import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.entity.Owner;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.repository.OwnerRepository;
import com.abhishekvermaa10.service.OwnerService;
import com.abhishekvermaa10.util.OwnerMapper;
import com.abhishekvermaa10.util.OwnerPetInfoMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

    private final MessageSource messageSource;
	
	private final OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;	
	private final OwnerPetInfoMapper ownerPetInfoMapper;
	
	private static final String OWNER_NOT_FOUND_KEY = "owner.not.found";

	@Override
	public Integer saveOwner(OwnerDTO ownerDTO) {
		Owner owner = ownerMapper.ownerDTOToOwner(ownerDTO);
		Owner save = ownerRepository.save(owner);
		return ownerMapper.ownerToOwnerDTO(save).getId();
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		return ownerRepository.findById(ownerId)
				.map(ownerMapper::ownerToOwnerDTO)
				.map(this::formatDates)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(getMessage(OWNER_NOT_FOUND_KEY), ownerId)));
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(getMessage(OWNER_NOT_FOUND_KEY), ownerId)));
		owner.getPet().setName(petName);
		ownerRepository.save(owner);
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		boolean ownerExists = ownerRepository.existsById(ownerId);
		if (!ownerExists) {
			throw new OwnerNotFoundException(String.format(getMessage(OWNER_NOT_FOUND_KEY), ownerId));
		} else {
			ownerRepository.deleteById(ownerId);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll()
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}
	
	@Override
	public List<Object[]> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(int pageNumber,
			int numberOfRecordsPerPage) {
		Pageable pageable = PageRequest.of(pageNumber, numberOfRecordsPerPage);
		return ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable);
	}

	@Override
	public Page<OwnerPetInfoDTO> findOwnerPetDetailsAsPage(Pageable pageable) {
		Page<Object[]> page = ownerRepository.findIdAndFirstNameAndLastNameAndPetNamePage(pageable);
		List<OwnerPetInfoDTO> list = page.stream()
			.map(ownerPetInfoMapper::mapObjectArrayToOwnerPetInfoDTO)
			.toList();
		return new PageImpl<>(list, pageable, page.getTotalElements());
	}
	
	private OwnerDTO formatDates(OwnerDTO ownerDTO) {
		PetDTO petDTO = ownerDTO.getPetDTO();
		if(petDTO instanceof DomesticPetDTO domesticPetDTO) {
			domesticPetDTO.setFormattedBirthDate(formatLocalDate(domesticPetDTO.getBirthDate()));
		}
		return ownerDTO;
	}
	
	private String formatLocalDate(LocalDate localDate) {
		return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
				.withLocale(LocaleContextHolder.getLocale())
				.format(localDate);
	}
	
	private String getMessage(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}
	
}
