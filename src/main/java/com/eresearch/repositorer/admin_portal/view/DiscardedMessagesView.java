package com.eresearch.repositorer.admin_portal.view;

import com.eresearch.repositorer.admin_portal.dto.discarded_messages.response.DiscardedMessage;
import com.eresearch.repositorer.admin_portal.dto.discarded_messages.response.RetrievedDiscardedMessageDto;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;
import com.eresearch.repositorer.admin_portal.lazy.model.GeneralLazyDataModel;
import com.eresearch.repositorer.admin_portal.primefaces.PrimefacesHelper;
import com.eresearch.repositorer.admin_portal.service.DiscardedMessagesService;
import io.vavr.Tuple;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class DiscardedMessagesView implements Serializable {

    @ManagedProperty("#{discardedMessagesService}")
    private DiscardedMessagesService discardedMessagesService;

    @ManagedProperty("#{primefacesHelper}")
    private PrimefacesHelper primefacesHelper;

    private DiscardedMessage discardedMessage;

    private GeneralLazyDataModel<DiscardedMessage> discardedMessages;

    private DiscardedMessage selectedDiscardedMessage;

    private io.vavr.collection.Map<String, String> discardedMessageIdToFilename;

    public void init() {
        try {

            populateDiscardedMessages();

        } catch (Exception e) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, e);
        }
    }

    public void refresh(boolean showMessage) {
        try {
            populateDiscardedMessages();

            if (showMessage) {
                primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Successful Refresh!",
                        "All discarded messages refreshed successfully."));

            }
        } catch (AdminPortalException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Refresh Failure!",
                    "Could not refresh discarded messages."));

        } catch (NoSuchFieldException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Refresh Fatal Failure!",
                    "Could not refresh discarded messages."));
        }
    }

    public void deleteAll() {

        try {
            discardedMessagesService.deleteAll();
            populateDiscardedMessages();

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Deletion!",
                    "All discarded messages deleted successfully."));

        } catch (AdminPortalException ex) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Deletion Failure!",
                    "Could not delete discarded messages."));

        } catch (NoSuchFieldException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Deletion Fatal Failure!",
                    "Could not delete discarded messages."));

        }
    }

    public void delete() {

        if (selectedDiscardedMessage == null) {
            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selection required!",
                    "Please select a discarded message first and try again."));
            return;
        }

        try {
            discardedMessagesService.delete(discardedMessageIdToFilename.apply(selectedDiscardedMessage.getId()));

            populateDiscardedMessages();

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Deletion!",
                    "Selected discarded message deleted successfully."));
        } catch (AdminPortalException ex) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Deletion Failure!",
                    "Could not delete selected discarded message."));

        } catch (NoSuchFieldException e) {
            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Deletion Fatal Failure!",
                    "Could not delete selected discarded message."));
        }

    }

    private void populateDiscardedMessages() throws NoSuchFieldException {
        Collection<RetrievedDiscardedMessageDto> retrievedDiscardedMessageDtos = discardedMessagesService.findAll().getRetrievedDiscardedMessageDtos();

        discardedMessageIdToFilename = io.vavr.collection.List.of(retrievedDiscardedMessageDtos)
                .flatMap(x -> x)
                .toMap(dto -> Tuple.of(dto.getDiscardedMessage().getId(), dto.getFilename()));

        List<DiscardedMessage> messages = io.vavr.collection.List.of(retrievedDiscardedMessageDtos)
                .flatMap(Function.identity())
                .map(RetrievedDiscardedMessageDto::getDiscardedMessage).asJava();

        Field idField = DiscardedMessage.class.getDeclaredField("id");

        discardedMessages = new GeneralLazyDataModel<>(messages, idField);
    }

}
