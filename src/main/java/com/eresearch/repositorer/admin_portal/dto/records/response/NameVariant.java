package com.eresearch.repositorer.admin_portal.dto.records.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NameVariant implements Serializable {

    private String firstname;
    private String initials;
    private String surname;
}
