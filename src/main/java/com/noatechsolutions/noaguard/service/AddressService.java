package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.AddressRequest;
import com.noatechsolutions.noaguard.dto.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse createAddress(AddressRequest request);
    List<AddressResponse> getAddressesByEntity(String entityType, Long entityId);
}
