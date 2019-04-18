package com.eresearch.repositorer.admin_portal.dto.records.response;

import com.eresearch.repositorer.admin_portal.domain.Metadata;
import io.vavr.Tuple2;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of = {"title", "authors"})
public class Entry implements Serializable {

    private String title;
    private Set<Author> authors;
    private Map<String, String> metadata;

    //NON-PERSISTENT-ATTRIBUTE
    //Note: we use list here in order primefaces datatable component perform sortable operations.
    private List<Author> sortableAuthors;

    //NON-PERSISTENT-ATTRIBUTE
    private List<Metadata> metadataList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(title.toLowerCase(), entry.title.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase());
    }
}
