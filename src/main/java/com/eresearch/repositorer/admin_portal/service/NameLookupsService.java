package com.eresearch.repositorer.admin_portal.service;

import com.eresearch.repositorer.admin_portal.dto.name_lookups.request.NameLookupSearchDto;
import com.eresearch.repositorer.admin_portal.dto.name_lookups.response.NameLookupDeleteOperationResultDto;
import com.eresearch.repositorer.admin_portal.dto.name_lookups.response.RetrievedNameLookupDtos;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;
import com.eresearch.repositorer.admin_portal.repository.NameLookupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class NameLookupsService {

    @Autowired
    private NameLookupsRepository nameLookupsRepository;

    public RetrievedNameLookupDtos findAll() {
        RetrievedNameLookupDtos dtos = nameLookupsRepository.findAll();

        if (dtos == null) {
            return new RetrievedNameLookupDtos(Collections.emptyList());
        }

        if (dtos.getNameLookups() == null || dtos.getNameLookups().isEmpty()) {
            dtos.setNameLookups(Collections.emptyList());
            return dtos;
        }

        return dtos;
    }

    public void delete(String id) {
        NameLookupDeleteOperationResultDto result = nameLookupsRepository.delete(new NameLookupSearchDto(id));

        if (!result.isOperationSuccess()) {
            throw new AdminPortalException(AdminPortalError.BUSINESS_OPERATION_FAILED.getMessage(), AdminPortalError.BUSINESS_OPERATION_FAILED);
        }
    }

    public void deleteAll() {
        NameLookupDeleteOperationResultDto result = nameLookupsRepository.deleteAll();

        if (!result.isOperationSuccess()) {
            throw new AdminPortalException(AdminPortalError.BUSINESS_OPERATION_FAILED.getMessage(), AdminPortalError.BUSINESS_OPERATION_FAILED);
        }
    }

}
