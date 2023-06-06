package com.example.easybook;

public class TrainerClass {
    private String name;

    private String uid;
    private String description;
    private String satisfiedUsers;

    public TrainerClass(String name, String satisfiedUsers, String description, String uid) {
        this.name = name;
        this.satisfiedUsers = satisfiedUsers;
        this.description = description;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSatisfiedUsers() {return satisfiedUsers;}

    public String getUid(){return uid;}
}
