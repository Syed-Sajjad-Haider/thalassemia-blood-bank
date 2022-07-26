package com.techbuddies.thalassemiabloodbank.models;

public class patientRegistrationmodel
{
    String pateintID , childName , guardianName , pateintGender , DiseaseType , dob , phoneNumber ,password ,address ,bloodGroup;

    public patientRegistrationmodel(String pateintID, String childName, String guardianName, String pateintGender, String diseaseType, String dob, String phoneNumber, String password, String address, String bloodGroup) {
        this.pateintID = pateintID;
        this.childName = childName;
        this.guardianName = guardianName;
        this.pateintGender = pateintGender;
        DiseaseType = diseaseType;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
        this.bloodGroup = bloodGroup;
    }

    public patientRegistrationmodel() {
    }

    public String getPateintID() {
        return pateintID;
    }

    public void setPateintID(String pateintID) {
        this.pateintID = pateintID;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getPateintGender() {
        return pateintGender;
    }

    public void setPateintGender(String pateintGender) {
        this.pateintGender = pateintGender;
    }

    public String getDiseaseType() {
        return DiseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        DiseaseType = diseaseType;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
