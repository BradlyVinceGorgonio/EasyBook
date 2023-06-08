package com.example.easybook;

public class BookRequestClass
{
    private String name;

    private String uid;

    private String location;
    private String schedule;

    private String level;

    public BookRequestClass(String name, String schedule, String uid, String level, String location) {
        this.name = name;
        this.schedule = schedule;
        this.uid = uid;
        this.level = level;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getLocation() {return location;}

    public String getUid(){return uid;}

    public String getLevel(){return level;}
}
