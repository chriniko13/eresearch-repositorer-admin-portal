package com.eresearch.repositorer.admin_portal.dto.records.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordSearchResultDto {

    private Collection<RetrievedRecordDto> retrievedRecordDtos;
}
