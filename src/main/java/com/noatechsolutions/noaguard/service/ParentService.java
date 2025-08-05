package com.noatechsolutions.noaguard.service;

import com.noatechsolutions.noaguard.dto.ParentRequest;
import com.noatechsolutions.noaguard.dto.ParentResponse;
import com.noatechsolutions.noaguard.dto.ParentUpdateRequest;

import java.util.List;

public interface ParentService {

    ParentResponse createParent(ParentRequest request);

    ParentResponse updateParent(Long id, ParentUpdateRequest request);

    ParentResponse getParentById(Long id);

    List<ParentResponse> getAllParents();

    List<ParentResponse> getParentsByStudentId(Long studentId);

    void deleteParent(Long id);
}
