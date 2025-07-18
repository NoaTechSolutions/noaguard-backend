package com.noatechsolutions.noaguard.mapper;

import com.noatechsolutions.noaguard.dto.AddressResponse;
import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.entity.Address;
import com.noatechsolutions.noaguard.entity.Daycare;
import com.noatechsolutions.noaguard.entity.Parent;
import com.noatechsolutions.noaguard.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class ParentMapper {

    public Parent toEntity(ParentRequest request, Student student, Daycare daycare) {
        Parent parent = new Parent();
        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());
        parent.setEmail(request.getEmail());
        parent.setPhone(request.getPhone());

        if (request.getAddress() != null) {
            Address address = new Address();
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setZipCode(request.getAddress().getZipCode());
            parent.setAddress(address);
        }

        parent.setStudent(student);
        parent.setDaycare(daycare);
        return parent;
    }

    public ParentResponse toResponse(Parent parent) {
        AddressResponse addressResponse = null;

        if (parent.getAddress() != null) {
            Address address = parent.getAddress();
            addressResponse = new AddressResponse();
            addressResponse.setStreet(address.getStreet());
            addressResponse.setCity(address.getCity());
            addressResponse.setState(address.getState());
            addressResponse.setZipCode(address.getZipCode());
            addressResponse.setCountry(address.getCountry());
        }

        return new ParentResponse(
                parent.getId(),
                parent.getFirstName(),
                parent.getLastName(),
                parent.getEmail(),
                parent.getPhone(),
                addressResponse,
                parent.getDaycare() != null ? parent.getDaycare().getId() : null,
                parent.getStudent() != null ? parent.getStudent().getId() : null,
                parent.getCreatedAt(),
                parent.getUpdatedAt()
        );
    }
}