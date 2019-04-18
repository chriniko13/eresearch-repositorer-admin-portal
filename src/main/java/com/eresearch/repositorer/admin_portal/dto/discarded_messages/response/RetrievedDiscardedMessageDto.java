package com.eresearch.repositorer.admin_portal.dto.discarded_messages.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"filename"})
@EqualsAndHashCode(of = {"filename"})

@JsonInclude(value = JsonInclude.Include.NON_NULL) //in order to not include null discarded message.
public class RetrievedDiscardedMessageDto {

    private String filename;
    private DiscardedMessage discardedMessage;
}
