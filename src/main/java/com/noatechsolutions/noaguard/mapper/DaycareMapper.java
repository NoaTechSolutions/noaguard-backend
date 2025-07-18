package com.noatechsolutions.noaguard.mapper;

import com.noatechsolutions.noaguard.dto.AddressRequest;
import com.noatechsolutions.noaguard.dto.AddressResponse;
import com.noatechsolutions.noaguard.dto.DaycareRequest;
import com.noatechsolutions.noaguard.dto.DaycareResponse;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.entity.Daycare;
import org.springframework.stereotype.Component;

@Component
public class DaycareMapper {

    public Daycare toEntity(DaycareRequest request) {
        if (request == null) return null;
        Daycare daycare = new Daycare();
        daycare.setName(request.getName());
        daycare.setLogoUrl(request.getLogoUrl());
        daycare.setPhone(request.getPhone());
        daycare.setEmail(request.getEmail());
        daycare.setAddress(addressRequestToAddress(request.getAddress()));
        return daycare;
    }

    public DaycareResponse toResponse(Daycare daycare) {
        if (daycare == null) return null;
        DaycareResponse response = new DaycareResponse();
        response.setId(daycare.getId());
        response.setName(daycare.getName());
        response.setLogoUrl(daycare.getLogoUrl());
        response.setPhone(daycare.getPhone());
        response.setEmail(daycare.getEmail());
        response.setAddress(addressToResponse(daycare.getAddress()));
        response.setCreatedAt(daycare.getCreatedAt());
        response.setUpdatedAt(daycare.getUpdatedAt());
        response.setUpdatedBy(daycare.getUpdatedBy());
        return response;
    }

    public Address addressRequestToAddress(AddressRequest request) {
        if (request == null) return null;
        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setCountry(request.getCountry());
        return address;
    }

    public AddressResponse addressToResponse(Address address) {
        if (address == null) return null;
        AddressResponse response = new AddressResponse();
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setZipCode(address.getZipCode());
        response.setCountry(address.getCountry());
        return response;
    }
}