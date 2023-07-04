package com.example.myapplication.data.model.movie;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "movies_result")
public class MovieResults {

	@SerializedName("overview")
	@Expose
	private String overview;
	@SerializedName("original_language")
	@Expose
	private String originalLanguage;
	@SerializedName("original_title")
	@Expose
	private String originalTitle;
	@SerializedName("video")
	@Expose
	private boolean video;
	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("genre_ids")
	@Expose
	private List<Integer> genreIds;
	@SerializedName("poster_path")
	@Expose
	private String posterPath;
	@SerializedName("backdrop_path")
	@Expose
	private String backdropPath;
	@SerializedName("release_date")
	@Expose
	private String releaseDate;
	@SerializedName("popularity")
	@Expose
	private String popularity;
	@SerializedName("vote_average")
	@Expose
	private String voteAverage;
	@SerializedName("id")
	@Expose
	@PrimaryKey
	private int id;
	@SerializedName("adult")
	@Expose
	private boolean adult;
	@SerializedName("vote_count")
	@Expose
	private int voteCount;


	@Expose
	private boolean isFavourite = false;

	public boolean isFavourite() {
		return isFavourite;
	}

	public void setFavourite(boolean favourite) {
		isFavourite = favourite;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setGenreIds(List<Integer> genreIds) {
		this.genreIds = genreIds;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setPopularity(String popularity) {
		this.popularity = popularity;
	}

	public void setVoteAverage(String voteAverage) {
		this.voteAverage = voteAverage;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public MovieResults() {
	}

	public MovieResults(String overview, String originalLanguage, String originalTitle, boolean video, String title, List<Integer> genreIds, String posterPath, String backdropPath, String releaseDate, String popularity, String voteAverage, int id, boolean adult, int voteCount, boolean isFavourite) {
		this.overview = overview;
		this.originalLanguage = originalLanguage;
		this.originalTitle = originalTitle;
		this.video = video;
		this.title = title;
		this.genreIds = genreIds;
		this.posterPath = posterPath;
		this.backdropPath = backdropPath;
		this.releaseDate = releaseDate;
		this.popularity = popularity;
		this.voteAverage = voteAverage;
		this.id = id;
		this.adult = adult;
		this.voteCount = voteCount;
		this.isFavourite = isFavourite;
	}

	public String getOverview() {
		return overview;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public boolean isVideo() {
		return video;
	}

	public String getTitle() {
		return title;
	}

	public List<Integer> getGenreIds() {
		return genreIds;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getPopularity() {
		return popularity;
	}

	public String getVoteAverage() {
		return voteAverage;
	}

	public int getId() {
		return id;
	}

	public boolean isAdult() {
		return adult;
	}

	public int getVoteCount() {
		return voteCount;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"overview = '" + overview + '\'' + 
			",original_language = '" + originalLanguage + '\'' + 
			",original_title = '" + originalTitle + '\'' + 
			",video = '" + video + '\'' + 
			",title = '" + title + '\'' + 
			",genre_ids = '" + genreIds + '\'' + 
			",poster_path = '" + posterPath + '\'' + 
			",backdrop_path = '" + backdropPath + '\'' + 
			",release_date = '" + releaseDate + '\'' + 
			",popularity = '" + popularity + '\'' + 
			",vote_average = '" + voteAverage + '\'' + 
			",id = '" + id + '\'' + 
			",adult = '" + adult + '\'' + 
			",vote_count = '" + voteCount + '\'' + 
			"}";
		}
}