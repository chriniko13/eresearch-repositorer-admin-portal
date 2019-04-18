package com.eresearch.repositorer.admin_portal.service;

import com.eresearch.repositorer.admin_portal.dto.error_reports.request.ErrorReportFilenameDto;
import com.eresearch.repositorer.admin_portal.dto.error_reports.response.ErrorReportDeleteOperationResultDto;
import com.eresearch.repositorer.admin_portal.dto.error_reports.response.ErrorReportDeleteOperationStatus;
import com.eresearch.repositorer.admin_portal.dto.error_reports.response.RetrievedErrorReportDtos;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;
import com.eresearch.repositorer.admin_portal.repository.ErrorReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ErrorReportsService {

    @Autowired
    private ErrorReportsRepository errorReportsRepository;

    public RetrievedErrorReportDtos findAll() {

        RetrievedErrorReportDtos reportDtos = errorReportsRepository.findAll();

        if (reportDtos == null) {
            return new RetrievedErrorReportDtos(Collections.emptyList());
        }

        if (reportDtos.getRetrievedErrorReportDtos() == null || reportDtos.getRetrievedErrorReportDtos().isEmpty()) {
            reportDtos.setRetrievedErrorReportDtos(Collections.emptyList());
            return reportDtos;
        }

        return reportDtos;
    }

    public void delete(String filename) {

        ErrorReportDeleteOperationResultDto result = errorReportsRepository.delete(new ErrorReportFilenameDto(filename));

        if (!result.isOperationSuccess()
                || result.getErrorReportDeleteOperationStatus() != ErrorReportDeleteOperationStatus.SUCCESS) {
            throw new AdminPortalException(AdminPortalError.BUSINESS_OPERATION_FAILED.getMessage(), AdminPortalError.BUSINESS_OPERATION_FAILED);
        }

    }

    public void deleteAll() {

        ErrorReportDeleteOperationResultDto result = errorReportsRepository.deleteAll();

        if (!result.isOperationSuccess()
                || result.getErrorReportDeleteOperationStatus() != ErrorReportDeleteOperationStatus.SUCCESS) {
            throw new AdminPortalException(AdminPortalError.BUSINESS_OPERATION_FAILED.getMessage(), AdminPortalError.BUSINESS_OPERATION_FAILED);
        }

    }
}
