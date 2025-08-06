package com.noatechsolutions.noaguard.mapper;

import com.noatechsolutions.noaguard.dto.AddressRequest;
import com.noatechsolutions.noaguard.dto.AddressResponse;
import com.noatechsolutions.noaguard.dto.AddressUpdateRequest;
import com.noatechsolutions.noaguard.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    // Mapeo desde AddressRequest (crear)
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

    // Mapeo desde AddressUpdateRequest (actualizar)
    public Address toEntity(AddressUpdateRequest request) {
        if (request == null) return null;
        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setCountry(request.getCountry());
        // entityId y entityType no se envían en updates, se asignan en el servicio
        return address;
    }

    // Conversión a respuesta
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

    public void partialUpdate(Address address, AddressRequest request) {
        if (request == null || address == null) return;

        if (request.getStreet() != null) address.setStreet(request.getStreet());
        if (request.getCity() != null) address.setCity(request.getCity());
        if (request.getState() != null) address.setState(request.getState());
        if (request.getZipCode() != null) address.setZipCode(request.getZipCode());
        if (request.getCountry() != null) address.setCountry(request.getCountry());
    }
}
