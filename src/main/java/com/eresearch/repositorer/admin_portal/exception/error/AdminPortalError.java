package com.eresearch.repositorer.admin_portal.exception.error;

public enum AdminPortalError {

    FATAL_ERROR("Application fatal error occurred."),
    BUSINESS_OPERATION_FAILED("Could not perform business operation."),
    CONNECTION_ERROR("Could not connect to external system.");

    private final String message;

    AdminPortalError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
