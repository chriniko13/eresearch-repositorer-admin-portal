package com.eresearch.repositorer.admin_portal.dto.error_reports.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorReportFilenameDto {

    @JsonProperty("filename")
    private String filename;
}
