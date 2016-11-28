package com.sohba_travel.sohba.models;

/**
 * Created by hazem on 11/26/2016.
 */

public class NewUserHost {
    private String uId;
    private String Type;
    private String fName;
    private String lName;
    private String birthdate;
    private String gender;
    private String mobile;
    private String email;
    private String language;
    private String verified;
    private String profileImage;
    private String frontIdImage;
    private String backIdImage;
    private String selfieImage;
    private String interests;
    private String nationality;
    private String job;

    //public empty constructor;
    public NewUserHost() {
    }

    public NewUserHost(String uId, String type, String fName, String lName, String birthdate, String gender, String mobile, String email, String language, String verified, String profileImage, String frontIdImage, String backIdImage, String selfieImage, String interests, String nationality, String job) {
        this.uId = uId;
        Type = type;
        this.fName = fName;
        this.lName = lName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
        this.language = language;
        this.verified = verified;
        this.profileImage = profileImage;
        this.frontIdImage = frontIdImage;
        this.backIdImage = backIdImage;
        this.selfieImage = selfieImage;
        this.interests = interests;
        this.nationality = nationality;
        this.job = job;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getFrontIdImage() {
        return frontIdImage;
    }

    public void setFrontIdImage(String frontIdImage) {
        this.frontIdImage = frontIdImage;
    }

    public String getBackIdImage() {
        return backIdImage;
    }

    public void setBackIdImage(String backIdImage) {
        this.backIdImage = backIdImage;
    }

    public String getSelfieImage() {
        return selfieImage;
    }

    public void setSelfieImage(String selfieImage) {
        this.selfieImage = selfieImage;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
