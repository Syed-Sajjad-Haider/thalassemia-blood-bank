package com.techbuddies.thalassemiabloodbank.models;

public class bloodRequest {

    String id , currentuserID , pateintName , bloodGrp , hospitalName , hbLevel , phoneNumP;

    public bloodRequest(String id, String currentuserID, String pateintName, String bloodGrp, String hospitalName, String hbLevel, String phoneNumP) {
        this.id = id;
        this.currentuserID = currentuserID;
        this.pateintName = pateintName;
        this.bloodGrp = bloodGrp;
        this.hospitalName = hospitalName;
        this.hbLevel = hbLevel;
        this.phoneNumP = phoneNumP;
    }

    public bloodRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentuserID() {
        return currentuserID;
    }

    public void setCurrentuserID(String currentuserID) {
        this.currentuserID = currentuserID;
    }

    public String getPateintName() {
        return pateintName;
    }

    public void setPateintName(String pateintName) {
        this.pateintName = pateintName;
    }

    public String getBloodGrp() {
        return bloodGrp;
    }

    public void setBloodGrp(String bloodGrp) {
        this.bloodGrp = bloodGrp;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHbLevel() {
        return hbLevel;
    }

    public void setHbLevel(String hbLevel) {
        this.hbLevel = hbLevel;
    }

    public String getPhoneNumP() {
        return phoneNumP;
    }

    public void setPhoneNumP(String phoneNumP) {
        this.phoneNumP = phoneNumP;
    }
}
