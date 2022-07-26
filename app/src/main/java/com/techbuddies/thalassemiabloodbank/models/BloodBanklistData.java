package com.techbuddies.thalassemiabloodbank.models;

public class BloodBanklistData {

    String id , bankName , bankAdress , bankContact;

    public BloodBanklistData() {
    }

    public BloodBanklistData(String id, String bankName, String bankAdress, String bankContact) {
        this.id = id;
        this.bankName = bankName;
        this.bankAdress = bankAdress;
        this.bankContact = bankContact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAdress() {
        return bankAdress;
    }

    public void setBankAdress(String bankAdress) {
        this.bankAdress = bankAdress;
    }

    public String getBankContact() {
        return bankContact;
    }

    public void setBankContact(String bankContact) {
        this.bankContact = bankContact;
    }
}
