package com.example.myapplication.domain.model.detail;

public class SpokenLanguagesItem {
	private String name;
	private String iso6391;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setIso6391(String iso6391) {
		this.iso6391 = iso6391;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public SpokenLanguagesItem() {
	}

	public SpokenLanguagesItem(String name, String iso6391, String englishName) {
		this.name = name;
		this.iso6391 = iso6391;
		this.englishName = englishName;
	}
}
