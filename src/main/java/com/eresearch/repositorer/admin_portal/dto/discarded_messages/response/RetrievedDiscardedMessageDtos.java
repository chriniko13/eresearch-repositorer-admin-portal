package com.eresearch.repositorer.admin_portal.dto.discarded_messages.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetrievedDiscardedMessageDtos {

    private Collection<RetrievedDiscardedMessageDto> retrievedDiscardedMessageDtos;
}
