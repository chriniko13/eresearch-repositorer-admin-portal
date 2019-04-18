package com.eresearch.repositorer.admin_portal.dto.name_lookups.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetrievedNameLookupDtos {

    private Collection<NameLookup> nameLookups;
}
