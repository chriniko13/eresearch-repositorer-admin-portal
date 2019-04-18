package com.eresearch.repositorer.admin_portal.converter;

import com.eresearch.repositorer.admin_portal.domain.Theme;
import com.eresearch.repositorer.admin_portal.service.ThemeService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("themeConverter")
public class ThemeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {

                ThemeService themeService = (ThemeService) fc.getExternalContext().getApplicationMap().get("themeService");
                return themeService.getThemes().stream().filter(theme -> theme.getName().equals(value)).findAny().orElseThrow(IllegalStateException::new);

            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Theme) object).getName());
        } else {
            return null;
        }
    }
}