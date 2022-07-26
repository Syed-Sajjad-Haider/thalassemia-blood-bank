package com.techbuddies.thalassemiabloodbank.models;

public class patientHistory {

    String id , currentuID , nameOfDonor , dataoftransfusion , placeoftransfusion , imgURl;

    public patientHistory(String id, String currentuID, String nameOfDonor, String dataoftransfusion, String placeoftransfusion, String imgURl) {
        this.id = id;
        this.currentuID = currentuID;
        this.nameOfDonor = nameOfDonor;
        this.dataoftransfusion = dataoftransfusion;
        this.placeoftransfusion = placeoftransfusion;
        this.imgURl = imgURl;
    }

    public patientHistory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentuID() {
        return currentuID;
    }

    public void setCurrentuID(String currentuID) {
        this.currentuID = currentuID;
    }

    public String getNameOfDonor() {
        return nameOfDonor;
    }

    public void setNameOfDonor(String nameOfDonor) {
        this.nameOfDonor = nameOfDonor;
    }

    public String getDataoftransfusion() {
        return dataoftransfusion;
    }

    public void setDataoftransfusion(String dataoftransfusion) {
        this.dataoftransfusion = dataoftransfusion;
    }

    public String getPlaceoftransfusion() {
        return placeoftransfusion;
    }

    public void setPlaceoftransfusion(String placeoftransfusion) {
        this.placeoftransfusion = placeoftransfusion;
    }

    public String getImgURl() {
        return imgURl;
    }

    public void setImgURl(String imgURl) {
        this.imgURl = imgURl;
    }
}
