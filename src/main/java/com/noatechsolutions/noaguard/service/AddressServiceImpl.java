package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.AddressRequest;
import com.noatechsolutions.noaguard.dto.AddressResponse;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.mapper.AddressMapper;
import com.noatechsolutions.noaguard.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressResponse createAddress(AddressRequest request) {
        Address address = addressMapper.toEntity(request);
        Address saved = addressRepository.save(address);
        return addressMapper.toResponse(saved);
    }

    @Override
    public List<AddressResponse> getAddressesByEntity(String entityType, Long entityId) {
        return addressRepository.findByEntityTypeAndEntityId(entityType, entityId)
                .stream()
                .map(addressMapper::toResponse)
                .collect(Collectors.toList());
    }
}
