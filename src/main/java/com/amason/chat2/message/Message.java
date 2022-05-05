package com.amason.chat2.message;

import java.math.BigInteger;

public class Message {
    private String text;
    private BigInteger dateAndTimeMilliSec;
    private String name;
    private String type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigInteger getDateAndTimeMilliSec() {
        return dateAndTimeMilliSec;
    }

    public void setDateAndTimeMilliSec(BigInteger date) {
        this.dateAndTimeMilliSec = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
