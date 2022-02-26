package com.example.quickfetch.model;

public class Bank {
    private final String name;
    private final String logo;

    public Bank(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }
}
