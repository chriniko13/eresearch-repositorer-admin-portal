package com.eresearch.repositorer.admin_portal.authentication;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static javax.faces.context.FacesContext.getCurrentInstance;

@ManagedBean
@RequestScoped
public class AuthenticationManager {


    public String doLogin() throws ServletException, IOException {
        ExternalContext externalContext = getCurrentInstance().getExternalContext();

        RequestDispatcher requestDispatcher = ((ServletRequest) externalContext.getRequest()).getRequestDispatcher("j_spring_security_check");
        requestDispatcher.forward((ServletRequest) externalContext.getRequest(), (ServletResponse) externalContext.getResponse());

        getCurrentInstance().responseComplete();

        return null;
    }

    public String doLogout() {

        final boolean isAlreadyLoggedIn = SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken);


        final FacesContext facesContext = getCurrentInstance();

        if (!isAlreadyLoggedIn) {

            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "You have not logged in!",
                    "You have not logged in and tried to log out, please get serious!"));
            return null;
        }

        Flash flash = getCurrentInstance().getExternalContext().getFlash();
        flash.setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Successful Logout!",
                "You have successfully logged out from portal!"));

        SecurityContextHolder.clearContext();

        return "/login.xhtml?faces-redirect=true";
    }

    public void displayLoginFailedMessage() {

        String failedLogin = getCurrentInstance().getExternalContext().getRequestParameterMap().get("failed");
        if (failedLogin != null && "true".equalsIgnoreCase(failedLogin)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Credentials",
                    "The credentials you've entered are invalid!"));
        }
    }

    public void displayLoginSuccessMessage() {
        String successfulLogin = getCurrentInstance().getExternalContext().getRequestParameterMap().get("success");
        if (successfulLogin != null && "true".equalsIgnoreCase(successfulLogin)) {
            getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successfull Login!", "You have logged in to the portal successfully!"));
        }
    }
}
