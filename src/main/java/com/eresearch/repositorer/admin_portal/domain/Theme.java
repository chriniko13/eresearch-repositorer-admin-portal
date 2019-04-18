package com.eresearch.repositorer.admin_portal.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Theme {

    private int id;

    private String displayName;

    private String name;

    @Override
    public String toString() {
        return name;
    }
}