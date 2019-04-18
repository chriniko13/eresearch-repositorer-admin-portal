package com.eresearch.repositorer.admin_portal.view;

import com.eresearch.repositorer.admin_portal.dto.error_reports.response.ErrorReport;
import com.eresearch.repositorer.admin_portal.dto.error_reports.response.RetrievedErrorReportDto;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;
import com.eresearch.repositorer.admin_portal.lazy.model.GeneralLazyDataModel;
import com.eresearch.repositorer.admin_portal.primefaces.PrimefacesHelper;
import com.eresearch.repositorer.admin_portal.service.ErrorReportsService;
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
public class ErrorReportsView implements Serializable {

    @ManagedProperty("#{errorReportsService}")
    private ErrorReportsService errorReportsService;

    @ManagedProperty("#{primefacesHelper}")
    private PrimefacesHelper primefacesHelper;

    private GeneralLazyDataModel<ErrorReport> errorReports;

    private ErrorReport selectedErrorReport;

    private io.vavr.collection.Map<String, String> errorReportIdToFilename;

    public void init() {
        try {

            populateErrorReports();

        } catch (Exception e) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, e);
        }
    }

    public void refresh(boolean showMessage) {
        try {
            populateErrorReports();

            if (showMessage) {
                primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Successful Refresh!",
                        "All error reports refreshed successfully."));
            }
        } catch (AdminPortalException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Refresh Failure!",
                    "Could not refresh error reports."));

        } catch (NoSuchFieldException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Refresh Fatal Failure!",
                    "Could not refresh error reports."));
        }
    }

    public void deleteAll() {

        try {
            errorReportsService.deleteAll();
            populateErrorReports();

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Deletion!",
                    "All error reports deleted successfully."));

        } catch (AdminPortalException ex) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Deletion Failure!",
                    "Could not delete error reports."));

        } catch (NoSuchFieldException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Deletion Fatal Failure!",
                    "Could not delete error reports."));

        }
    }

    public void delete() {

        if (selectedErrorReport == null) {
            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selection required!",
                    "Please select an error report first and try again."));
            return;
        }

        try {
            errorReportsService.delete(errorReportIdToFilename.apply(selectedErrorReport.getId()));

            populateErrorReports();

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Deletion!",
                    "Selected error report deleted successfully."));
        } catch (AdminPortalException ex) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Deletion Failure!",
                    "Could not delete selected error report."));

        } catch (NoSuchFieldException e) {
            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Deletion Fatal Failure!",
                    "Could not delete selected error report."));
        }

    }

    private void populateErrorReports() throws NoSuchFieldException {
        Collection<RetrievedErrorReportDto> retrievedErrorReportDtos = errorReportsService.findAll().getRetrievedErrorReportDtos();

        errorReportIdToFilename = io.vavr.collection.List.of(retrievedErrorReportDtos)
                .flatMap(x -> x)
                .toMap(dto -> Tuple.of(dto.getErrorReport().getId(), dto.getFilename()));

        List<ErrorReport> reports = io.vavr.collection.List.of(retrievedErrorReportDtos)
                .flatMap(Function.identity())
                .map(RetrievedErrorReportDto::getErrorReport).asJava();

        Field idField = ErrorReport.class.getDeclaredField("id");

        errorReports = new GeneralLazyDataModel<>(reports, idField);
    }

}
