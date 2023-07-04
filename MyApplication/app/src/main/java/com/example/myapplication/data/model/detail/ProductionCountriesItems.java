package com.example.myapplication.data.model.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCountriesItems {
	@SerializedName("iso31661")
	@Expose
	private String iso31661;
	@SerializedName("name")
	@Expose
	private String name;

	public String getIso31661(){
		return iso31661;
	}

	public String getName(){
		return name;
	}

	public ProductionCountriesItems(String iso31661, String name) {
		this.iso31661 = iso31661;
		this.name = name;
	}
}
