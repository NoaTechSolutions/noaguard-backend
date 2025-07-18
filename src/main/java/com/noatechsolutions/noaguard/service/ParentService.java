package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.dto.ParentUpdateRequest;

import java.util.List;

public interface ParentService {
    ParentResponse createParent(ParentRequest request);
    List<ParentResponse> getAllParents();
    ParentResponse getParentById(Long id);
    ParentResponse updateParent(Long id, ParentUpdateRequest request);
    void deleteParent(Long id);
}
