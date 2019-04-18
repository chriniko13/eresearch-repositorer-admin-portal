package com.eresearch.repositorer.admin_portal.dto.records.response;


import com.eresearch.repositorer.admin_portal.deserializer.InstantDeserializer;
import com.eresearch.repositorer.admin_portal.serializer.InstantSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;

/**
 * This domain class holds info about author extraction records.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Record implements Serializable {

    private String id;

    private String transactionId;

    private String firstname;
    private String initials;
    private String lastname;

    private Collection<NameVariant> nameVariants;

    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant createdAt;

    private Collection<Entry> entries;
}
