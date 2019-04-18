package com.eresearch.repositorer.admin_portal.view;

import com.eresearch.repositorer.admin_portal.dto.name_lookups.response.NameLookup;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;
import com.eresearch.repositorer.admin_portal.lazy.model.GeneralLazyDataModel;
import com.eresearch.repositorer.admin_portal.primefaces.PrimefacesHelper;
import com.eresearch.repositorer.admin_portal.service.NameLookupsService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class NameLookupsView implements Serializable {

    @ManagedProperty("#{nameLookupsService}")
    private NameLookupsService nameLookupsService;

    @ManagedProperty("#{primefacesHelper}")
    private PrimefacesHelper primefacesHelper;

    private GeneralLazyDataModel<NameLookup> nameLookups;

    private NameLookup selectedNameLookup;

    public void init() {
        try {
            populateNameLookups();
        } catch (Exception e) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, e);
        }
    }

    public void refresh(boolean showMessage) {
        try {
            populateNameLookups();

            if (showMessage) {
                primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Successful Refresh!",
                        "All name lookups refreshed successfully."));

            }
        } catch (AdminPortalException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Refresh Failure!",
                    "Could not refresh name lookups."));

        } catch (NoSuchFieldException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Refresh Fatal Failure!",
                    "Could not refresh name lookups."));
        }
    }

    public void deleteAll() {

        try {
            nameLookupsService.deleteAll();
            populateNameLookups();

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Deletion!",
                    "All name lookups deleted successfully."));

        } catch (AdminPortalException ex) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Deletion Failure!",
                    "Could not delete name lookups."));

        } catch (NoSuchFieldException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Deletion Fatal Failure!",
                    "Could not delete name lookups."));

        }
    }

    public void delete() {

        if (selectedNameLookup == null) {
            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selection required!",
                    "Please select a name lookup first and try again."));
            return;
        }

        try {
            nameLookupsService.delete(selectedNameLookup.getId());

            populateNameLookups();

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Deletion!",
                    "Selected name lookup deleted successfully."));
        } catch (AdminPortalException ex) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Deletion Failure!",
                    "Could not delete selected name lookup."));

        } catch (NoSuchFieldException e) {
            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Deletion Fatal Failure!",
                    "Could not delete selected name lookup."));
        }

    }

    private void populateNameLookups() throws NoSuchFieldException {
        List<NameLookup> retrievedNameLookups = new ArrayList<>(nameLookupsService.findAll().getNameLookups());

        Field idField = NameLookup.class.getDeclaredField("id");

        nameLookups = new GeneralLazyDataModel<NameLookup>(retrievedNameLookups, idField);
    }

}
