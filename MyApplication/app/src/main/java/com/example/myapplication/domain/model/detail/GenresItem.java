package com.example.myapplication.domain.model.detail;

public class GenresItem {
	private String name;
	private int id;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GenresItem() {
	}

	public GenresItem(String name, int id) {
		this.name = name;
		this.id = id;
	}

}
