package com.example.myapplication.data.model.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompaniesItems {
	@SerializedName("logo_path")
	@Expose
	private String logoPath;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("origin_country")
	@Expose
	private String originCountry;

	public String getLogoPath(){
		return logoPath;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getOriginCountry(){
		return originCountry;
	}
}
