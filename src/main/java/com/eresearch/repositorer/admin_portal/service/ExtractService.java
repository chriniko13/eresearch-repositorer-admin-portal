package com.eresearch.repositorer.admin_portal.service;

import com.eresearch.repositorer.admin_portal.domain.Author;
import com.eresearch.repositorer.admin_portal.dto.extract.request.RepositorerFindDto;
import com.eresearch.repositorer.admin_portal.dto.extract.request.RepositorerFindDtos;
import com.eresearch.repositorer.admin_portal.dto.extract.response.RepositorerImmediateResultDto;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.repository.ExtractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtractService {

    private static final String EXTRACTION_FIRED = "Extraction fired.";

    @Autowired
    private ExtractRepository extractRepository;

    public boolean performExtraction(List<Author> authors) {

        try {

            RepositorerFindDtos repositorerFindDtos = new RepositorerFindDtos(authors
                    .stream()
                    .map(author -> new RepositorerFindDto(author.getFirstname(), author.getInitials(), author.getSurname()))
                    .collect(Collectors.toList()));

            RepositorerImmediateResultDto repositorerImmediateResultDto = extractRepository.performExtraction(repositorerFindDtos);

            return EXTRACTION_FIRED.equals(repositorerImmediateResultDto.getMessage());

        } catch (AdminPortalException ex) {
            return false;
        }

    }

}
