package com.example.happycustomer1;

public class UserHelperClass {
    String signup_fullname,signup_username,signup_email,signup_password,gender,date,phoneNo;

    public UserHelperClass(){

    }
    public UserHelperClass(String signup_fullname, String signup_username, String signup_email, String signup_password, String gender, String date, String phoneNo) {
        this.signup_fullname = signup_fullname;
        this.signup_username = signup_username;
        this.signup_email = signup_email;
        this.signup_password = signup_password;
        this.gender = gender;
        this.date = date;
        this.phoneNo = phoneNo;
    }

    public String getSignup_fullname() {
        return signup_fullname;
    }

    public void setSignup_fullname(String signup_fullname) {
        this.signup_fullname = signup_fullname;
    }

    public String getSignup_username() {
        return signup_username;
    }

    public void setSignup_username(String signup_username) {
        this.signup_username = signup_username;
    }

    public String getSignup_email() {
        return signup_email;
    }

    public void setSignup_email(String signup_email) {
        this.signup_email = signup_email;
    }

    public String getSignup_password() {
        return signup_password;
    }

    public void setSignup_password(String signup_password) {
        this.signup_password = signup_password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
