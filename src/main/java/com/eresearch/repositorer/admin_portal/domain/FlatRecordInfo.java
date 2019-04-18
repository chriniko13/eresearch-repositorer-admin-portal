package com.eresearch.repositorer.admin_portal.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"filename"})
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlatRecordInfo {

    private String filename;

    private String firstname;

    private String initials;

    private String surname;

    private String creationDate;
}
