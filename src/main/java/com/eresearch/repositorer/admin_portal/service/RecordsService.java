package com.eresearch.repositorer.admin_portal.service;

import com.eresearch.repositorer.admin_portal.dto.records.request.RecordFilenameDto;
import com.eresearch.repositorer.admin_portal.dto.records.response.RecordDeleteOperationResultDto;
import com.eresearch.repositorer.admin_portal.dto.records.response.RecordSearchResultDto;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;
import com.eresearch.repositorer.admin_portal.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RecordsService {

    @Autowired
    private RecordsRepository recordsRepository;

    public RecordSearchResultDto findAll() {
        RecordSearchResultDto dtos = recordsRepository.findAll();

        if (dtos == null) {
            return new RecordSearchResultDto(Collections.emptyList());
        }

        if (dtos.getRetrievedRecordDtos() == null || dtos.getRetrievedRecordDtos().isEmpty()) {
            dtos.setRetrievedRecordDtos(Collections.emptyList());
            return dtos;
        }

        return dtos;
    }

    public RecordSearchResultDto find(String filename) {
        RecordSearchResultDto dtos = recordsRepository.find(new RecordFilenameDto(filename));

        if (dtos == null) {
            return new RecordSearchResultDto(Collections.emptyList());
        }

        if (dtos.getRetrievedRecordDtos() == null || dtos.getRetrievedRecordDtos().isEmpty()) {
            dtos.setRetrievedRecordDtos(Collections.emptyList());
            return dtos;
        }

        return dtos;
    }

    public void deleteAll() {
        RecordDeleteOperationResultDto result = recordsRepository.deleteAll();

        if (!result.isOperationSuccess()) {
            throw new AdminPortalException(AdminPortalError.BUSINESS_OPERATION_FAILED.getMessage(), AdminPortalError.BUSINESS_OPERATION_FAILED);
        }

    }

    public void delete(String filename) {
        RecordDeleteOperationResultDto result = recordsRepository.delete(new RecordFilenameDto(filename));

        if (!result.isOperationSuccess()) {
            throw new AdminPortalException(AdminPortalError.BUSINESS_OPERATION_FAILED.getMessage(), AdminPortalError.BUSINESS_OPERATION_FAILED);
        }
    }
}
