package com.example.myapplication.domain.model.detail;

public class ProductionCompaniesItem {
    private String logoPath;
    private String name;
    private int id;
    private String originCountry;

    public String getLogoPath() {
        return logoPath;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public ProductionCompaniesItem() {}

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public ProductionCompaniesItem(String logoPath, String name, int id, String originCountry) {
        this.logoPath = logoPath;
        this.name = name;
        this.id = id;
        this.originCountry = originCountry;
    }
}
