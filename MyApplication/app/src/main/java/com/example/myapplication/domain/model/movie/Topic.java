package com.example.myapplication.domain.model.movie;

public class Topic {
    public String key;
    public String value;

    public Topic(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
