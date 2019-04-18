package com.eresearch.repositorer.admin_portal.view;

import com.eresearch.repositorer.admin_portal.domain.Theme;
import com.eresearch.repositorer.admin_portal.primefaces.PrimefacesHelper;
import com.eresearch.repositorer.admin_portal.service.ThemeService;
import com.eresearch.repositorer.admin_portal.view.context.UserSelectedThemeContext;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class ThemeSelectionView implements Serializable {

    @ManagedProperty("#{themeService}")
    private ThemeService themeService;

    @ManagedProperty("#{userSelectedThemeContext}")
    private UserSelectedThemeContext userSelectedThemeContext;

    @ManagedProperty("#{primefacesHelper}")
    private PrimefacesHelper primefacesHelper;

    private List<Theme> themes;

    private Theme selectedTheme;

    public void init() {
        themes = themeService.getThemes();
    }

    public void saveTheme() {
        userSelectedThemeContext.setSelectedTheme(selectedTheme);

        primefacesHelper.addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Successful Theme Selection!",
                "Theme: " + selectedTheme + " selected successfully."));
    }
}
