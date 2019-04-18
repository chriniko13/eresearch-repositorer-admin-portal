package com.eresearch.repositorer.admin_portal.dto.name_lookups.response;

import com.eresearch.repositorer.admin_portal.deserializer.InstantDeserializer;
import com.eresearch.repositorer.admin_portal.serializer.InstantSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Collection;


/**
 * This domain class holds the binding between author name to perform extraction and transaction id.
 */

@Getter
@Setter
@ToString(of = {"firstname", "initials", "surname"})
@EqualsAndHashCode(of = {"firstname", "initials", "surname"})

public class NameLookup {

    private String id;

    private String transactionId;

    private String firstname;
    private String initials;
    private String surname;

    private Collection<NameVariant> nameVariants;

    private NameLookupStatus nameLookupStatus;

    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant createdAt;

    public NameLookup() {
    }

    public NameLookup(String transactionId,
                      String firstname,
                      String initials,
                      String surname,
                      Collection<NameVariant> nameVariants,
                      NameLookupStatus nameLookupStatus,
                      Instant createdAt) {
        this.transactionId = transactionId;
        this.firstname = firstname;
        this.initials = initials;
        this.surname = surname;
        this.nameVariants = nameVariants;
        this.nameLookupStatus = nameLookupStatus;
        this.createdAt = createdAt;
    }
}
