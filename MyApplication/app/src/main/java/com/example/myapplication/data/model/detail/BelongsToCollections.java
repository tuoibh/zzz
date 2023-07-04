package com.example.myapplication.data.model.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BelongsToCollections {
	@SerializedName("backdrop_path")
	@Expose
	private String backdropPath;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("poster_path")
	@Expose
	private String posterPath;

	public String getBackdropPath(){
		return backdropPath;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getPosterPath(){
		return posterPath;
	}
}
