package com.example.myapplication.data.model.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenresItems {
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("id")
	@Expose
	private int id;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public GenresItems(String name, int id) {
		this.name = name;
		this.id = id;
	}
}
