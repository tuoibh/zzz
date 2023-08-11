package com.example.myapplication.data.model.castncrew;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CrewItems {
	@SerializedName("gender")
	@Expose
	private int gender;
	@SerializedName("credit_id")
	@Expose
	private String creditId;
	@SerializedName("known_for_department")
	@Expose
	private String knownForDepartment;
	@SerializedName("original_name")
	@Expose
	private String originalName;
	@SerializedName("popularity")
	@Expose
	private Object popularity;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("profile_path")
	@Expose
	private String profilePath;
	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("adult")
	@Expose
	private boolean adult;
	@SerializedName("department")
	@Expose
	private String department;
	@SerializedName("job")
	@Expose
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

	public CrewItems() {
	}

	public CrewItems(int gender, String creditId, String knownForDepartment, String originalName, Object popularity, String name, String profilePath, int id, boolean adult, String department, String job) {
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

	@NonNull
	@Override
	public String toString() {
		return "CrewItems{" +
				"gender=" + gender +
				", creditId='" + creditId + '\'' +
				", knownForDepartment='" + knownForDepartment + '\'' +
				", originalName='" + originalName + '\'' +
				", popularity=" + popularity +
				", name='" + name + '\'' +
				", profilePath='" + profilePath + '\'' +
				", id=" + id +
				", adult=" + adult +
				", department='" + department + '\'' +
				", job='" + job + '\'' +
				'}';
	}
}
