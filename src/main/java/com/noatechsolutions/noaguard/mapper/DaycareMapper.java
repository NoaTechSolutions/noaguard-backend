package com.noatechsolutions.noaguard.mapper;

import com.noatechsolutions.noaguard.dto.DaycareRequest;
import com.noatechsolutions.noaguard.dto.DaycareResponse;
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
        if (daycare.getAdmin() != null) {
            response.setAdminId(daycare.getAdmin().getId());
        }
        response.setCreatedAt(daycare.getCreatedAt());
        response.setUpdatedAt(daycare.getUpdatedAt());
        response.setUpdatedBy(daycare.getUpdatedBy()); // ✅ nuevo
        return response;
    }
}
