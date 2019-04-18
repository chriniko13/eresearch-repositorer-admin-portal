package com.eresearch.repositorer.admin_portal.view;

import com.eresearch.repositorer.admin_portal.domain.FlatRecordInfo;
import com.eresearch.repositorer.admin_portal.domain.Metadata;
import com.eresearch.repositorer.admin_portal.dto.records.response.Entry;
import com.eresearch.repositorer.admin_portal.dto.records.response.Record;
import com.eresearch.repositorer.admin_portal.dto.records.response.RecordSearchResultDto;
import com.eresearch.repositorer.admin_portal.dto.records.response.RetrievedRecordDto;
import com.eresearch.repositorer.admin_portal.exception.AdminPortalException;
import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;
import com.eresearch.repositorer.admin_portal.lazy.model.GeneralLazyDataModel;
import com.eresearch.repositorer.admin_portal.primefaces.PrimefacesHelper;
import com.eresearch.repositorer.admin_portal.service.RecordsService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class RecordsView implements Serializable {


    @ManagedProperty("#{recordsService}")
    private RecordsService recordsService;

    @ManagedProperty("#{primefacesHelper}")
    private PrimefacesHelper primefacesHelper;

    private GeneralLazyDataModel<FlatRecordInfo> flatRecordInfos;

    private FlatRecordInfo selectedFlatRecordInfo;

    private Record selectedRecord;

    private Entry selectedEntry;

    public void init() {
        try {
            populateRecords();
        } catch (Exception e) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR, e);
        }
    }

    public void refresh(boolean showMessage) {
        try {
            populateRecords();

            if (showMessage) {
                primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Successful Refresh!",
                        "All records refreshed successfully."));

            }
        } catch (AdminPortalException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Refresh Failure!",
                    "Could not refresh records."));

        } catch (NoSuchFieldException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Refresh Fatal Failure!",
                    "Could not refresh records."));
        }
    }

    public void deleteAll() {

        try {
            recordsService.deleteAll();
            populateRecords();

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Deletion!",
                    "All records deleted successfully."));

        } catch (AdminPortalException ex) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Deletion Failure!",
                    "Could not delete records."));

        } catch (NoSuchFieldException e) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Deletion Fatal Failure!",
                    "Could not delete records."));

        }
    }

    public void fullLoadSelectedFlatRecord() {

        if (selectedFlatRecordInfo == null) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR);
        }

        RecordSearchResultDto recordSearchResultDto = recordsService.find(selectedFlatRecordInfo.getFilename());

        selectedRecord = Optional.ofNullable(recordSearchResultDto)
                .map(RecordSearchResultDto::getRetrievedRecordDtos)
                .filter(records -> records.size() == 1)
                .flatMap(records -> records.stream().findFirst())
                .map(RetrievedRecordDto::getRecord)
                .orElseThrow(() -> new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR));

        selectedEntry = null;

        RequestContext.getCurrentInstance().scrollTo("form:recordDetail");
    }

    public void fullLoadSelectedEntry() {

        if (selectedRecord == null) {
            throw new AdminPortalException(AdminPortalError.FATAL_ERROR.getMessage(), AdminPortalError.FATAL_ERROR);
        }

        populateSelectedRecordSortableAuthors();
        populateSelectedRecordMetadataList();

        RequestContext.getCurrentInstance().scrollTo("form:entryDetail");
    }

    private void populateSelectedRecordSortableAuthors() {
        selectedRecord.getEntries().forEach(entry -> entry.setSortableAuthors(new ArrayList<>(entry.getAuthors())));
    }

    private void populateSelectedRecordMetadataList() {
        selectedRecord.getEntries().forEach(entry -> {
            Set<Map.Entry<String, String>> metadata = entry.getMetadata().entrySet();

            if (metadata.isEmpty()) {

                entry.setMetadataList(Collections.emptyList());

            } else {

                List<Metadata> metadataAsList = io.vavr.collection.List.ofAll(metadata.stream())
                        .map(metadatum -> new Metadata(metadatum.getKey(), metadatum.getValue()))
                        .asJava();

                entry.setMetadataList(metadataAsList);
            }
        });
    }

    public void delete() {

        if (selectedFlatRecordInfo == null) {
            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Selection required!",
                    "Please select a record first and try again."));
            return;
        }

        try {
            recordsService.delete(selectedFlatRecordInfo.getFilename());

            populateRecords();

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successful Deletion!",
                    "Selected record deleted successfully."));
        } catch (AdminPortalException ex) {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Deletion Failure!",
                    "Could not delete selected record."));

        } catch (NoSuchFieldException e) {
            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Deletion Fatal Failure!",
                    "Could not delete selected record."));
        }

    }

    private void populateRecords() throws NoSuchFieldException {
        List<RetrievedRecordDto> recordDtos = new ArrayList<>(recordsService.findAll().getRetrievedRecordDtos());

        List<FlatRecordInfo> flatRecordInfos = io.vavr.collection.List.of(recordDtos)
                .flatMap(x -> x)
                .map(dto -> {
                    //filename contains something like: RECORDPanagiotis_NoValue_Liaparinos#2017-09-17T11:11:22.103
                    String filename = dto.getFilename();

                    String[] splittedInfo = filename.split("#");

                    String fullName = splittedInfo[0].replace("RECORD", "");
                    String[] splittedFullname = fullName.split("_");

                    String creationDate = splittedInfo[1];

                    return new FlatRecordInfo(dto.getFilename(),
                            splittedFullname[0],
                            splittedFullname[1],
                            splittedFullname[2],
                            creationDate);
                })
                .asJava();

        Field idField = FlatRecordInfo.class.getDeclaredField("filename");

        this.flatRecordInfos = new GeneralLazyDataModel<>(flatRecordInfos, idField);
    }


}
