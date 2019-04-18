package com.eresearch.repositorer.admin_portal.repository;

import com.eresearch.repositorer.admin_portal.dto.error_reports.request.ErrorReportFilenameDto;
import com.eresearch.repositorer.admin_portal.dto.error_reports.response.ErrorReportDeleteOperationResultDto;
import com.eresearch.repositorer.admin_portal.dto.error_reports.response.RetrievedErrorReportDtos;
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
public class ErrorReportsRepository {

    @Value("${repositorer.url}")
    private String repositorerUrl;

    private static final String REPOSITORER_ERROR_REPORTS_ENDPOINT = "/error-reports";

    @Autowired
    private RestTemplate restTemplate;

    public RetrievedErrorReportDtos findAll() {

        try {
            String url = repositorerUrl + REPOSITORER_ERROR_REPORTS_ENDPOINT + "/find-all";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("full-fetch", true);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<RetrievedErrorReportDtos> responseEntity
                    = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, RetrievedErrorReportDtos.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);
        }
    }


    public ErrorReportDeleteOperationResultDto delete(ErrorReportFilenameDto errorReportFilenameDto) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(errorReportFilenameDto, headers);

            ResponseEntity<ErrorReportDeleteOperationResultDto> responseEntity
                    = restTemplate.exchange(URI.create(repositorerUrl + REPOSITORER_ERROR_REPORTS_ENDPOINT), HttpMethod.DELETE, entity, ErrorReportDeleteOperationResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);

        }
    }

    public ErrorReportDeleteOperationResultDto deleteAll() {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<ErrorReportDeleteOperationResultDto> responseEntity
                    = restTemplate.exchange(URI.create(repositorerUrl + REPOSITORER_ERROR_REPORTS_ENDPOINT), HttpMethod.DELETE, entity, ErrorReportDeleteOperationResultDto.class);

            return responseEntity.getBody();

        } catch (RestClientException ex) {
            throw new AdminPortalException(AdminPortalError.CONNECTION_ERROR.getMessage(), AdminPortalError.CONNECTION_ERROR, ex);
        } catch (Exception ex) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, ex);

        }
    }
}
