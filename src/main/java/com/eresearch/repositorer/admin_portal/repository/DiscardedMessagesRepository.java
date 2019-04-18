package com.eresearch.repositorer.admin_portal.repository;

import com.eresearch.repositorer.admin_portal.dto.discarded_messages.request.DiscaredMessageFilenameDto;
import com.eresearch.repositorer.admin_portal.dto.discarded_messages.response.DiscardedMessageDeleteOperationResultDto;
import com.eresearch.repositorer.admin_portal.dto.discarded_messages.response.RetrievedDiscardedMessageDtos;
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
public class DiscardedMessagesRepository {

    @Value("${repositorer.url}")
    private String repositorerUrl;

    private static final String REPOSITORER_DISCARDED_MESSAGES_ENDPOINT = "/discarded-messages";

    @Autowired
    private RestTemplate restTemplate;

    public RetrievedDiscardedMessageDtos findAll() {
        try {
            String url = repositorerUrl + REPOSITORER_DISCARDED_MESSAGES_ENDPOINT + "/find-all";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("full-fetch", true);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<RetrievedDiscardedMessageDtos> responseEntity
                    = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, RetrievedDiscardedMessageDtos.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);
        }
    }

    public DiscardedMessageDeleteOperationResultDto deleteAll() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<DiscardedMessageDeleteOperationResultDto> responseEntity
                    = restTemplate.exchange(URI.create(repositorerUrl + REPOSITORER_DISCARDED_MESSAGES_ENDPOINT), HttpMethod.DELETE, entity, DiscardedMessageDeleteOperationResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);

        }
    }

    public DiscardedMessageDeleteOperationResultDto delete(DiscaredMessageFilenameDto discaredMessageFilenameDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(discaredMessageFilenameDto, headers);

            ResponseEntity<DiscardedMessageDeleteOperationResultDto> responseEntity
                    = restTemplate.exchange(URI.create(repositorerUrl + REPOSITORER_DISCARDED_MESSAGES_ENDPOINT), HttpMethod.DELETE, entity, DiscardedMessageDeleteOperationResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);

        }
    }
}
