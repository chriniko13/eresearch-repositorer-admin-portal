package com.eresearch.repositorer.admin_portal.repository;

import com.eresearch.repositorer.admin_portal.dto.name_lookups.request.NameLookupSearchDto;
import com.eresearch.repositorer.admin_portal.dto.name_lookups.response.NameLookupDeleteOperationResultDto;
import com.eresearch.repositorer.admin_portal.dto.name_lookups.response.RetrievedNameLookupDtos;
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
public class NameLookupsRepository {

    @Value("${repositorer.url}")
    private String repositorerUrl;

    private static final String REPOSITORER_NAME_LOOKUPS_ENDPOINT = "/name-lookups";

    @Autowired
    private RestTemplate restTemplate;


    public RetrievedNameLookupDtos findAll() {
        try {
            String url = repositorerUrl + REPOSITORER_NAME_LOOKUPS_ENDPOINT + "/find-all";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<RetrievedNameLookupDtos> responseEntity
                    = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, RetrievedNameLookupDtos.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);
        }
    }


    public NameLookupDeleteOperationResultDto deleteAll() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<NameLookupDeleteOperationResultDto> responseEntity
                    = restTemplate.exchange(URI.create(repositorerUrl + REPOSITORER_NAME_LOOKUPS_ENDPOINT), HttpMethod.DELETE, entity, NameLookupDeleteOperationResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);
        }
    }


    public NameLookupDeleteOperationResultDto delete(NameLookupSearchDto nameLookupSearchDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(nameLookupSearchDto, headers);

            ResponseEntity<NameLookupDeleteOperationResultDto> responseEntity
                    = restTemplate.exchange(URI.create(repositorerUrl + REPOSITORER_NAME_LOOKUPS_ENDPOINT), HttpMethod.DELETE, entity, NameLookupDeleteOperationResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);
        }
    }

}
