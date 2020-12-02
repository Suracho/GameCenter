package com.example.sixteen;

public class Member {
    String image,username,address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Member() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Member(String image, String username, String address) {
        this.image = image;
        this.username = username;
        this.address = address;
    }
}
