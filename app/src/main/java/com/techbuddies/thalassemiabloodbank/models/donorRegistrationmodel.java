package com.techbuddies.thalassemiabloodbank.models;

public class donorRegistrationmodel {

    String donorid , donorName , donorFname , donorGender , donorDOB , donorPhoneNo , donorPassword , donorAddress , donorBloodGrp , donorHBLevel;

    public donorRegistrationmodel(String donorid, String donorName, String donorFname, String donorGender, String donorDOB, String donorPhoneNo, String donorPassword, String donorAddress, String donorBloodGrp , String donorHBLevel) {
        this.donorid = donorid;
        this.donorName = donorName;
        this.donorFname = donorFname;
        this.donorGender = donorGender;
        this.donorDOB = donorDOB;
        this.donorPhoneNo = donorPhoneNo;
        this.donorPassword = donorPassword;
        this.donorAddress = donorAddress;
        this.donorBloodGrp = donorBloodGrp;
        this.donorHBLevel = donorHBLevel;
    }

    public donorRegistrationmodel() {
    }


    public String getDonorid() {
        return donorid;
    }

    public void setDonorid(String donorid) {
        this.donorid = donorid;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorFname() {
        return donorFname;
    }

    public void setDonorFname(String donorFname) {
        this.donorFname = donorFname;
    }

    public String getDonorGender() {
        return donorGender;
    }

    public void setDonorGender(String donorGender) {
        this.donorGender = donorGender;
    }

    public String getDonorDOB() {
        return donorDOB;
    }

    public void setDonorDOB(String donorDOB) {
        this.donorDOB = donorDOB;
    }

    public String getDonorPhoneNo() {
        return donorPhoneNo;
    }

    public void setDonorPhoneNo(String donorPhoneNo) {
        this.donorPhoneNo = donorPhoneNo;
    }

    public String getDonorPassword() {
        return donorPassword;
    }

    public void setDonorPassword(String donorPassword) {
        this.donorPassword = donorPassword;
    }

    public String getDonorAddress() {
        return donorAddress;
    }

    public void setDonorAddress(String donorAddress) {
        this.donorAddress = donorAddress;
    }

    public String getDonorBloodGrp() {
        return donorBloodGrp;
    }

    public void setDonorBloodGrp(String donorBloodGrp) {
        this.donorBloodGrp = donorBloodGrp;
    }

    public String getDonorHBLevel() {
        return donorHBLevel;
    }

    public void setDonorHBLevel(String donorHBLevel) {
        this.donorHBLevel = donorHBLevel;
    }
}
