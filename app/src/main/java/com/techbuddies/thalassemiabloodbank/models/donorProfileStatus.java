package com.techbuddies.thalassemiabloodbank.models;

public class donorProfileStatus {

    String currentUserID , status;

    public donorProfileStatus(String currentUserID, String status) {
        this.currentUserID = currentUserID;
        this.status = status;
    }

    public donorProfileStatus() {
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
