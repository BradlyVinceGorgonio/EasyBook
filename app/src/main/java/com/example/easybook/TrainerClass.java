package com.example.easybook;

public class TrainerClass {
    private String name;
    private String description;
    private String satisfiedUsers;

    public TrainerClass(String name, String satisfiedUsers, String description) {
        this.name = name;
        this.satisfiedUsers = satisfiedUsers;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSatisfiedUsers() {return satisfiedUsers;}
}
