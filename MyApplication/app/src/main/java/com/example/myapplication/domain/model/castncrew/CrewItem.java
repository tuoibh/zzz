package com.example.myapplication.domain.model.castncrew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrewItem {
	private int gender;
	private String creditId;
	private String knownForDepartment;
	private String originalName;
	private Object popularity;
	private String name;
	private String profilePath;
	private int id;
	private boolean adult;
	private String department;
	private String job;

	public int getGender(){
		return gender;
	}

	public String getCreditId(){
		return creditId;
	}

	public String getKnownForDepartment(){
		return knownForDepartment;
	}

	public String getOriginalName(){
		return originalName;
	}

	public Object getPopularity(){
		return popularity;
	}

	public String getName(){
		return name;
	}

	public String getProfilePath(){
		return profilePath;
	}

	public int getId(){
		return id;
	}

	public boolean isAdult(){
		return adult;
	}

	public String getDepartment(){
		return department;
	}

	public String getJob(){
		return job;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}

	public void setKnownForDepartment(String knownForDepartment) {
		this.knownForDepartment = knownForDepartment;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public void setPopularity(Object popularity) {
		this.popularity = popularity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public CrewItem() {
	}

	public CrewItem(int gender, String creditId, String knownForDepartment, String originalName, Object popularity, String name, String profilePath, int id, boolean adult, String department, String job) {
		this.gender = gender;
		this.creditId = creditId;
		this.knownForDepartment = knownForDepartment;
		this.originalName = originalName;
		this.popularity = popularity;
		this.name = name;
		this.profilePath = profilePath;
		this.id = id;
		this.adult = adult;
		this.department = department;
		this.job = job;
	}
}
