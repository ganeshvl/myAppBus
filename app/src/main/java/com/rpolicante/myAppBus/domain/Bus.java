package com.rpolicante.myAppBus.domain;

/**
 * Created by policante on 7/29/15.
 */
public class Bus {
    private String number;
    private String name;

    public Bus(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
