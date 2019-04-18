package com.eresearch.repositorer.admin_portal.view.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@Getter
@ManagedBean
@ApplicationScoped
public class ViewConfigurator implements Serializable {

    @Value("${table.pollers.enabled}")
    private boolean tablePollersEnabled;

    @Value("${entry.title.escape.html}")
    private boolean entryTitleEscapeHtml;
}
