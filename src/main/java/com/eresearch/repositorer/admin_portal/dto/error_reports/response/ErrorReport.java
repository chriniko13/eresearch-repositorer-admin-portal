package com.eresearch.repositorer.admin_portal.dto.error_reports.response;


import com.eresearch.repositorer.admin_portal.deserializer.InstantDeserializer;
import com.eresearch.repositorer.admin_portal.serializer.InstantSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * This domain class holds info about error(s) raised-produced in our service.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorReport implements Serializable {

    private String id;

    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant createdAt;

    private String exceptionToString;
    private RepositorerError repositorerError;
    private String crashedComponentName;
    private String errorStacktrace;

    private String failedMessage;
}
