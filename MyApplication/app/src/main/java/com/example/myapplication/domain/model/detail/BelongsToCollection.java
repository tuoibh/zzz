package com.example.myapplication.domain.model.detail;

public class BelongsToCollection {
	private String backdropPath;
	private String name;
	private int id;
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

	public BelongsToCollection() {
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public BelongsToCollection(String backdropPath, String name, int id, String posterPath) {
		this.backdropPath = backdropPath;
		this.name = name;
		this.id = id;
		this.posterPath = posterPath;
	}
}
