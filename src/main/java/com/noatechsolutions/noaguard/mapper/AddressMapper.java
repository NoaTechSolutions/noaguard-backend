package com.noatechsolutions.noaguard.mapper;

import com.noatechsolutions.noaguard.dto.AddressRequest;
import com.noatechsolutions.noaguard.dto.AddressResponse;
import com.noatechsolutions.noaguard.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequest request) {
        if (request == null) return null;
        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setCountry(request.getCountry());
        address.setEntityId(request.getEntityId());
        address.setEntityType(request.getEntityType());
        return address;
    }

    public AddressResponse toResponse(Address address) {
        if (address == null) return null;
        AddressResponse response = new AddressResponse();
        response.setId(address.getId());
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setZipCode(address.getZipCode());
        response.setCountry(address.getCountry());
        response.setEntityId(address.getEntityId());
        response.setEntityType(address.getEntityType());
        return response;
    }
}
