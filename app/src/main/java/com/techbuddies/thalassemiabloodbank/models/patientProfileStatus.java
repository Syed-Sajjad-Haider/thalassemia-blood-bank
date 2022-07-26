package com.techbuddies.thalassemiabloodbank.models;

public class patientProfileStatus {

    String currentUserID , status;

    public patientProfileStatus(String currentUserID, String status) {
        this.currentUserID = currentUserID;
        this.status = status;
    }

    public patientProfileStatus() {
    }

    public String getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
