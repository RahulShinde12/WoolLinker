package com.capcun.newwoollinker;

public class chats_datalead {


    String number,message,date;

    public chats_datalead(String number, String message,String date) {
        this.number = number;
        this.message = message;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
