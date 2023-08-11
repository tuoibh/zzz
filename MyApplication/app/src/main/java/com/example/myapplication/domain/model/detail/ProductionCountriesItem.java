package com.example.myapplication.domain.model.detail;

public class ProductionCountriesItem {
	private String iso31661;
	private String name;

	public String getIso31661(){
		return iso31661;
	}

	public String getName(){
		return name;
	}

	public ProductionCountriesItem() {}

	public void setIso31661(String iso31661) {
		this.iso31661 = iso31661;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductionCountriesItem(String iso31661, String name) {
		this.iso31661 = iso31661;
		this.name = name;
	}
}
