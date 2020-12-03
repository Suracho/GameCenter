package com.example.sixteen;

public class Messages {
    //Model Class
    String username,image,email,location;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        String temp1 = "email: " + email + " username: " + username+ " location: " + location;
        return temp1;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        String temp1 = email + " " + username+ " " + location;

        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Messages(String username, String image, String email, String location) {
        this.username = username;
        this.image = image;
        this.email = email;
        this.location = location;
    }

    public Messages() {
    }
}
