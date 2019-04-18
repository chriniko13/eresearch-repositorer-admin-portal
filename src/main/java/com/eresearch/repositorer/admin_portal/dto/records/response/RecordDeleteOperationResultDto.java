package com.eresearch.repositorer.admin_portal.dto.records.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordDeleteOperationResultDto {

    private boolean operationSuccess;
    private RecordDeleteOperationStatus recordDeleteOperationStatus;
}
