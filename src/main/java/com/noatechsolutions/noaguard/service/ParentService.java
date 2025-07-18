package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.dto.ParentUpdateRequest;

import java.util.List;

public interface ParentService {

    ParentResponse createParent(ParentRequest request);

    ParentResponse getParentById(Long id);

    List<ParentResponse> getAllParents();

    ParentResponse updateParent(Long id, ParentUpdateRequest request);

    void deleteParent(Long id);
}