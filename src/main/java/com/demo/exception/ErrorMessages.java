package com.demo.exception;

public enum ErrorMessages {
    NO_RECORD_FOUND("No record found"),
    CONFLICT_OFFERS("user has offer at discount and deal, please check the data");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
