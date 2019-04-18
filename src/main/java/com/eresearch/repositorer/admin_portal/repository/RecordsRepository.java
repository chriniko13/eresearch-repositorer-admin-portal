package com.eresearch.repositorer.admin_portal.repository;

import com.eresearch.repositorer.admin_portal.dto.records.request.RecordFilenameDto;
import com.eresearch.repositorer.admin_portal.dto.records.response.RecordDeleteOperationResultDto;
import com.eresearch.repositorer.admin_portal.dto.records.response.RecordSearchResultDto;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Repository
public class RecordsRepository {

    @Value("${repositorer.url}")
    private String repositorerUrl;

    private static final String REPOSITORER_NAME_LOOKUPS_ENDPOINT = "/records";

    @Autowired
    private RestTemplate restTemplate;

    public RecordSearchResultDto findAll() {
        try {
            String url = repositorerUrl + REPOSITORER_NAME_LOOKUPS_ENDPOINT + "/find-all";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("full-fetch", false);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<RecordSearchResultDto> responseEntity
                    = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, RecordSearchResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);
        }
    }


    public RecordSearchResultDto find(RecordFilenameDto recordFilenameDto) {

        try {

            String url = repositorerUrl + REPOSITORER_NAME_LOOKUPS_ENDPOINT + "/find-by-filename";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("full-fetch", true);

            HttpEntity<?> entity = new HttpEntity<>(recordFilenameDto, headers);

            ResponseEntity<RecordSearchResultDto> responseEntity
                    = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity, RecordSearchResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);
        }
    }


    public RecordDeleteOperationResultDto deleteAll() {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<RecordDeleteOperationResultDto> responseEntity
                    = restTemplate.exchange(URI.create(repositorerUrl + REPOSITORER_NAME_LOOKUPS_ENDPOINT), HttpMethod.DELETE, entity, RecordDeleteOperationResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);
        }
    }

    public RecordDeleteOperationResultDto delete(RecordFilenameDto recordFilenameDto) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(recordFilenameDto, headers);

            ResponseEntity<RecordDeleteOperationResultDto> responseEntity
                    = restTemplate.exchange(URI.create(repositorerUrl + REPOSITORER_NAME_LOOKUPS_ENDPOINT), HttpMethod.DELETE, entity, RecordDeleteOperationResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);
        }
    }

}
