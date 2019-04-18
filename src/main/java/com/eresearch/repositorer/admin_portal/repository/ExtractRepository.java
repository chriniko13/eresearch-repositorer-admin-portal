package com.eresearch.repositorer.admin_portal.repository;

import com.eresearch.repositorer.admin_portal.dto.extract.request.RepositorerFindDtos;
import com.eresearch.repositorer.admin_portal.dto.extract.response.RepositorerImmediateResultDto;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Repository
public class ExtractRepository {

    @Value("${repositorer.url}")
    private String repositorerUrl;

    private static final String REPOSITORER_EXTRACT_ENDPOINT = "/extract/list";

    @Autowired
    private RestTemplate restTemplate;


    public RepositorerImmediateResultDto performExtraction(RepositorerFindDtos dtos) {

        try {
            String url = repositorerUrl + REPOSITORER_EXTRACT_ENDPOINT;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(dtos, headers);

            ResponseEntity<RepositorerImmediateResultDto> responseEntity
                    = restTemplate.exchange(URI.create(url), HttpMethod.POST, entity, RepositorerImmediateResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);
        }

    }
}
