package com.eresearch.repositorer.admin_portal.exception;

import com.eresearch.repositorer.admin_portal.exception.error.AdminPortalError;


public class AdminPortalException extends RuntimeException {

    private final AdminPortalError adminPortalError;

    public AdminPortalException(String message, AdminPortalError adminPortalError, Throwable cause) {
        super(message, cause);
        this.adminPortalError = adminPortalError;
    }

    public AdminPortalException(String message, AdminPortalError adminPortalError) {
        super(message);
        this.adminPortalError = adminPortalError;
    }

    public AdminPortalError getAdminPortalError() {
        return adminPortalError;
    }
}
