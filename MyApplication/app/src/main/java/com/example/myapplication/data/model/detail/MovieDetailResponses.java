package com.example.myapplication.data.model.detail;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieDetailResponses {
	@SerializedName("original_language")
	@Expose
	private String originalLanguage;
	@SerializedName("imdb_id")
	@Expose
	private String imdbId;
	@SerializedName("video")
	@Expose
	private boolean video;
	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("backdrop_path")
	@Expose
	private String backdropPath;
	@SerializedName("revenue")
	@Expose
	private int revenue;
	@SerializedName("genres")
	@Expose
	private List<GenresItems> genres;
	@SerializedName("popularity")
	@Expose
	private Object popularity;
	@SerializedName("production_countries")
	@Expose
	private List<ProductionCountriesItems> productionCountries;
	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("vote_count")
	@Expose
	private int voteCount;
	@SerializedName("budget")
	@Expose
	private int budget;
	@SerializedName("overview")
	@Expose
	private String overview;
	@SerializedName("original_title")
	@Expose
	private String originalTitle;
	@SerializedName("runtime")
	@Expose
	private int runtime;
	@SerializedName("poster_path")
	@Expose
	private String posterPath;
	@SerializedName("spoken_languages")
	@Expose
	private List<SpokenLanguagesItems> spokenLanguages;
	@SerializedName("production_companies")
	@Expose
	private List<ProductionCompaniesItems> productionCompanies;
	@SerializedName("release_date")
	@Expose
	private String releaseDate;
	@SerializedName("vote_average")
	@Expose
	private Object voteAverage;
	@SerializedName("belongs_to_collection")
	@Expose
	private BelongsToCollections belongsToCollection;
	@SerializedName("tagline")
	@Expose
	private String tagline;
	@SerializedName("adult")
	@Expose
	private boolean adult;
	@SerializedName("homepage")
	@Expose
	private String homepage;
	@SerializedName("status")
	@Expose
	private String status;
	@Expose
	private boolean isFavourite;

	public boolean isFavourite() {
		return isFavourite;
	}

	public void setFavourite(boolean favourite) {
		isFavourite = favourite;
	}


	public String getOriginalLanguage(){
		return originalLanguage;
	}

	public String getImdbId(){
		return imdbId;
	}

	public boolean isVideo(){
		return video;
	}

	public String getTitle(){
		return title;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public int getRevenue(){
		return revenue;
	}

	public List<GenresItems> getGenres(){
		return genres;
	}

	public Object getPopularity(){
		return popularity;
	}

	public List<ProductionCountriesItems> getProductionCountries(){
		return productionCountries;
	}

	public int getId(){
		return id;
	}

	public int getVoteCount(){
		return voteCount;
	}

	public int getBudget(){
		return budget;
	}

	public String getOverview(){
		return overview;
	}

	public String getOriginalTitle(){
		return originalTitle;
	}

	public int getRuntime(){
		return runtime;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public List<SpokenLanguagesItems> getSpokenLanguages(){
		return spokenLanguages;
	}

	public List<ProductionCompaniesItems> getProductionCompanies(){
		return productionCompanies;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public Object getVoteAverage(){
		return voteAverage;
	}

	public BelongsToCollections getBelongsToCollection(){
		return belongsToCollection;
	}

	public String getTagline(){
		return tagline;
	}

	public boolean isAdult(){
		return adult;
	}

	public String getHomepage(){
		return homepage;
	}

	public String getStatus(){
		return status;
	}

	public MovieDetailResponses() {
	}

	public MovieDetailResponses(String originalLanguage, String imdbId, boolean video, String title, String backdropPath, int revenue, List<GenresItems> genres, Object popularity, List<ProductionCountriesItems> productionCountries, int id, int voteCount, int budget, String overview, String originalTitle, int runtime, String posterPath, List<SpokenLanguagesItems> spokenLanguages, List<ProductionCompaniesItems> productionCompanies, String releaseDate, Object voteAverage, BelongsToCollections belongsToCollection, String tagline, boolean adult, String homepage, String status, boolean isFavourite) {
		this.originalLanguage = originalLanguage;
		this.imdbId = imdbId;
		this.video = video;
		this.title = title;
		this.backdropPath = backdropPath;
		this.revenue = revenue;
		this.genres = genres;
		this.popularity = popularity;
		this.productionCountries = productionCountries;
		this.id = id;
		this.voteCount = voteCount;
		this.budget = budget;
		this.overview = overview;
		this.originalTitle = originalTitle;
		this.runtime = runtime;
		this.posterPath = posterPath;
		this.spokenLanguages = spokenLanguages;
		this.productionCompanies = productionCompanies;
		this.releaseDate = releaseDate;
		this.voteAverage = voteAverage;
		this.belongsToCollection = belongsToCollection;
		this.tagline = tagline;
		this.adult = adult;
		this.homepage = homepage;
		this.status = status;
		this.isFavourite = isFavourite;
	}

	@NonNull
	@Override
	public String toString() {
		return "MovieDetailResponses{" +
				"originalLanguage='" + originalLanguage + '\'' +
				", imdbId='" + imdbId + '\'' +
				", video=" + video +
				", title='" + title + '\'' +
				", backdropPath='" + backdropPath + '\'' +
				", revenue=" + revenue +
				", genres=" + genres +
				", popularity=" + popularity +
				", productionCountries=" + productionCountries +
				", id=" + id +
				", voteCount=" + voteCount +
				", budget=" + budget +
				", overview='" + overview + '\'' +
				", originalTitle='" + originalTitle + '\'' +
				", runtime=" + runtime +
				", posterPath='" + posterPath + '\'' +
				", spokenLanguages=" + spokenLanguages +
				", productionCompanies=" + productionCompanies +
				", releaseDate='" + releaseDate + '\'' +
				", voteAverage=" + voteAverage +
				", belongsToCollection=" + belongsToCollection +
				", tagline='" + tagline + '\'' +
				", adult=" + adult +
				", homepage='" + homepage + '\'' +
				", status='" + status + '\'' +
				", isFavourite=" + isFavourite +
				'}';
	}
}