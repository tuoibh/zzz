package com.example.myapplication.domain.model.user;

import android.net.Uri;

public class User {
    private int id;
    private String name;
    private String email;
    private String date_of_birth;
    private String gender;

    private Uri uri;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User() {
    }

    public User(int id, String name, String email, String date_of_birth, String gender, Uri uri) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", gender='" + gender + '\'' +
                ", uri=" + uri +
                '}';
    }
}
