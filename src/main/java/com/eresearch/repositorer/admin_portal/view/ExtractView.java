package com.eresearch.repositorer.admin_portal.view;

import com.eresearch.repositorer.admin_portal.domain.Author;
import com.eresearch.repositorer.admin_portal.primefaces.PrimefacesHelper;
import com.eresearch.repositorer.admin_portal.service.ExtractService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class ExtractView implements Serializable {

    @ManagedProperty("#{extractService}")
    private ExtractService extractService;

    @ManagedProperty("#{primefacesHelper}")
    private PrimefacesHelper primefacesHelper;

    private Author author = new Author();

    private List<Author> authors = new LinkedList<>();

    public void init() {
        //Note: add your code here if needed during prerendering of page.
    }

    public String reinit() {
        author = new Author();
        return null;
    }

    public void performExtraction() {

        if (authors == null || authors.isEmpty()) {
            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Author List is Empty!",
                    "Please add at least one author record to perform extraction."));
            return;
        }

        boolean extractionTriggeredSuccessfully = extractService.performExtraction(authors);

        if (extractionTriggeredSuccessfully) {

            authors.clear();
            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Extraction Success!",
                    "Author extraction triggered successfully."));

        } else {

            primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Extraction Failure!",
                    "Author extraction triggered and a failure occurred."));

        }
    }
}
