package com.example.myapplication.data.model.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpokenLanguagesItems {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("iso6391")
	@Expose
	private String iso6391;
	@SerializedName("english_name")
	@Expose
	private String englishName;

	public String getName(){
		return name;
	}

	public String getIso6391(){
		return iso6391;
	}

	public String getEnglishName(){
		return englishName;
	}

	public SpokenLanguagesItems(String name, String iso6391, String englishName) {
		this.name = name;
		this.iso6391 = iso6391;
		this.englishName = englishName;
	}
}
