package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.DaycareRequest;
import com.noatechsolutions.noaguard.dto.DaycareResponse;
import com.noatechsolutions.noaguard.dto.DaycareUpdateRequest;

import java.util.List;

public interface DaycareService {
    DaycareResponse createDaycare(DaycareRequest request);
    DaycareResponse updateDaycare(Long id, DaycareUpdateRequest request);
    DaycareResponse getDaycareById(Long id);
    List<DaycareResponse> getAllDaycares();
    void deleteDaycare(Long id);
    DaycareResponse toggleDaycareActive(Long id);
}
