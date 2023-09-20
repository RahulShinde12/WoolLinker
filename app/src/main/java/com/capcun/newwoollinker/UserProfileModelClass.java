package com.capcun.newwoollinker;

public class UserProfileModelClass {

    String user_name,user_phone,user_mail,user_role,user_pass;
    int user_id;

    public UserProfileModelClass(int user_id,String user_name, String user_phone, String user_mail, String user_role, String user_pass) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_mail = user_mail;
        this.user_role = user_role;
        this.user_pass = user_pass;
    }

    public String getUser_name() {
        return user_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }
}
