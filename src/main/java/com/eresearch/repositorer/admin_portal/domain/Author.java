package com.eresearch.repositorer.admin_portal.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {

    private String firstname;
    private String initials;
    private String surname;
}
