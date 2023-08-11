package com.example.myapplication.data.model.castncrew;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CastNCrewResponses {
	@SerializedName("cast")
	@Expose
	private List<CastItems> cast;
	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("crew")
	@Expose
	private List<CrewItems> crew;

	public List<CastItems> getCast(){
		return cast;
	}

	public int getId(){
		return id;
	}

	public List<CrewItems> getCrew(){
		return crew;
	}

	public CastNCrewResponses() {
	}

	public CastNCrewResponses(List<CastItems> cast, int id, List<CrewItems> crew) {
		this.cast = cast;
		this.id = id;
		this.crew = crew;
	}

	@NonNull
	@Override
	public String toString() {
		return "CastNCrewResponses{" +
				"cast=" + cast +
				", id=" + id +
				", crew=" + crew +
				'}';
	}
}
