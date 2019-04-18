package com.eresearch.repositorer.admin_portal.service;

import com.eresearch.repositorer.admin_portal.dto.discarded_messages.request.DiscaredMessageFilenameDto;
import com.eresearch.repositorer.admin_portal.dto.discarded_messages.response.DiscardedMessageDeleteOperationResultDto;
import com.eresearch.repositorer.admin_portal.dto.discarded_messages.response.DiscardedMessageDeleteOperationStatus;
import com.eresearch.repositorer.admin_portal.dto.discarded_messages.response.RetrievedDiscardedMessageDtos;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;
import com.eresearch.repositorer.admin_portal.repository.DiscardedMessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DiscardedMessagesService {

    @Autowired
    private DiscardedMessagesRepository discardedMessagesRepository;


    public RetrievedDiscardedMessageDtos findAll() {

        RetrievedDiscardedMessageDtos discardedMessageDtos = discardedMessagesRepository.findAll();

        if (discardedMessageDtos == null) {
            return new RetrievedDiscardedMessageDtos(Collections.emptyList());
        }

        if (discardedMessageDtos.getRetrievedDiscardedMessageDtos() == null || discardedMessageDtos.getRetrievedDiscardedMessageDtos().isEmpty()) {
            discardedMessageDtos.setRetrievedDiscardedMessageDtos(Collections.emptyList());
            return discardedMessageDtos;
        }

        return discardedMessageDtos;

    }

    public void delete(String filename) {
        DiscardedMessageDeleteOperationResultDto result = discardedMessagesRepository.delete(new DiscaredMessageFilenameDto(filename));

        if (!result.isOperationSuccess()
                || result.getDiscardedMessageDeleteOperationStatus() != DiscardedMessageDeleteOperationStatus.SUCCESS) {
            throw new AdminPortalException(AdminPortalError.BUSINESS_OPERATION_FAILED.getMessage(), AdminPortalError.BUSINESS_OPERATION_FAILED);
        }
    }

    public void deleteAll() {
        DiscardedMessageDeleteOperationResultDto result = discardedMessagesRepository.deleteAll();

        if (!result.isOperationSuccess()
                || result.getDiscardedMessageDeleteOperationStatus() != DiscardedMessageDeleteOperationStatus.SUCCESS) {
            throw new AdminPortalException(AdminPortalError.BUSINESS_OPERATION_FAILED.getMessage(), AdminPortalError.BUSINESS_OPERATION_FAILED);
        }
    }

}
