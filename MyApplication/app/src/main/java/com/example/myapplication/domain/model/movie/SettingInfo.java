package com.example.myapplication.domain.model.movie;

public class SettingInfo {
    String keyTopic;
    float point;
    String keySort;
    int year;

    public String getKeyTopic() {
        return keyTopic;
    }

    public void setKeyTopic(String keyTopic) {
        this.keyTopic = keyTopic;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public String getKeySort() {
        return keySort;
    }

    public void setKeySort(String keySort) {
        this.keySort = keySort;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public SettingInfo(String keyTopic, float point, String keySort, int year) {
        this.keyTopic = keyTopic;
        this.point = point;
        this.keySort = keySort;
        this.year = year;
    }
}
