package com.eresearch.repositorer.admin_portal.view.context;

import com.eresearch.repositorer.admin_portal.domain.Theme;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@Getter
@Setter
@ManagedBean
@SessionScoped
public class UserSelectedThemeContext implements Serializable {

    private Theme selectedTheme = new Theme(16, "Flick", "flick");
}
