package com.example.myapplication.domain.model.castncrew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastNCrewResponse {
	private List<CastItem> cast;
	private int id;
	private List<CrewItem> crew;

	public List<CastItem> getCast(){
		return cast;
	}

	public int getId(){
		return id;
	}

	public List<CrewItem> getCrew(){
		return crew;
	}

	public CastNCrewResponse() {
	}

	public void setCast(List<CastItem> cast) {
		this.cast = cast;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCrew(List<CrewItem> crew) {
		this.crew = crew;
	}

	public CastNCrewResponse(List<CastItem> cast, int id, List<CrewItem> crew) {
		this.cast = cast;
		this.id = id;
		this.crew = crew;
	}

	@Override
	public String toString() {
		return "CastNCrewResponse{" +
				"cast=" + cast +
				", id=" + id +
				", crew=" + crew +
				'}';
	}
}
