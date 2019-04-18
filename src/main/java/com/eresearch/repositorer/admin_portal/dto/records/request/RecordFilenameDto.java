package com.eresearch.repositorer.admin_portal.dto.records.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordFilenameDto {

    @JsonProperty("filename")
    private String filename;
}
