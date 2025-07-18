package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.DaycareRequest;
import com.noatechsolutions.noaguard.dto.DaycareResponse;
import com.noatechsolutions.noaguard.dto.DaycareUpdateRequest;

import java.util.List;

public interface DaycareService {

    DaycareResponse createDaycare(DaycareRequest request);

    DaycareResponse getDaycareById(Long id);

    List<DaycareResponse> getAllDaycares();

    DaycareResponse updateDaycare(Long id, DaycareUpdateRequest request);

    void deleteDaycare(Long id);
}