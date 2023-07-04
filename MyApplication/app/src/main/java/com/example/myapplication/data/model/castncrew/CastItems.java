package com.example.myapplication.data.model.castncrew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CastItems {
	@SerializedName("cast_id")
	@Expose
	private int castId;
	@SerializedName("character")
	@Expose
	private String character;
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
	@SerializedName("order")
	@Expose
	private int order;

	public int getCastId(){
		return castId;
	}

	public String getCharacter(){
		return character;
	}

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

	public int getOrder(){
		return order;
	}

	public CastItems() {
	}

	public CastItems(int castId, String character, int gender, String creditId, String knownForDepartment, String originalName, Object popularity, String name, String profilePath, int id, boolean adult, int order) {
		this.castId = castId;
		this.character = character;
		this.gender = gender;
		this.creditId = creditId;
		this.knownForDepartment = knownForDepartment;
		this.originalName = originalName;
		this.popularity = popularity;
		this.name = name;
		this.profilePath = profilePath;
		this.id = id;
		this.adult = adult;
		this.order = order;
	}

	@Override
	public String toString() {
		return "CastItems{" +
				"castId=" + castId +
				", character='" + character + '\'' +
				", gender=" + gender +
				", creditId='" + creditId + '\'' +
				", knownForDepartment='" + knownForDepartment + '\'' +
				", originalName='" + originalName + '\'' +
				", popularity=" + popularity +
				", name='" + name + '\'' +
				", profilePath='" + profilePath + '\'' +
				", id=" + id +
				", adult=" + adult +
				", order=" + order +
				'}';
	}
}
