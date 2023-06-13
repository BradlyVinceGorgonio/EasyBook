package com.example.easybook;

public class AdminClass
{
    private String profilePictureUrl;

    private String name;

    private String uid;

    private String category;

    private String reason;

    private String price;



    public AdminClass(String profilePictureUrl, String name, String uid, String category, String reason, String price) {
        this.profilePictureUrl = profilePictureUrl;
        this.name = name;
        this.uid = uid;
        this.category = category;
        this.reason = reason;
        this.price = price;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getCategory() {
        return category;
    }

    public String getReason() {
        return reason;
    }

    public String getPrice() {
        return price;
    }
}

