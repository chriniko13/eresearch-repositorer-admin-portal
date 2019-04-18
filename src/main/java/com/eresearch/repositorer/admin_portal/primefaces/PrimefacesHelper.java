package com.eresearch.repositorer.admin_portal.primefaces;

import org.primefaces.context.PrimeFacesContext;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;

@Component
public class PrimefacesHelper {

    public void addMessage(String clientId, FacesMessage facesMessage) {
        PrimeFacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
    }

    public void addMessage(FacesMessage facesMessage) {
        addMessage(null, facesMessage);
    }

}
