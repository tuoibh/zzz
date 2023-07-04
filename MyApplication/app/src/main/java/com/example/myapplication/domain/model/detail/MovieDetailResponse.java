package com.example.myapplication.domain.model.detail;

import com.example.myapplication.core.AppConfig;

import java.util.List;

public class MovieDetailResponse {
	private String originalLanguage;
	private String imdbId;
	private boolean video;
	private String title;
	private String backdropPath;
	private int revenue;
	private List<GenresItem> genres;
	private Object popularity;
	private List<ProductionCountriesItem> productionCountries;
	private int id;
	private int voteCount;
	private int budget;
	private String overview;
	private String originalTitle;
	private int runtime;
	private String posterPath;
	private List<SpokenLanguagesItem> spokenLanguages;
	private List<ProductionCompaniesItem> productionCompanies;
	private String releaseDate;
	private Object voteAverage;
	private BelongsToCollection belongsToCollection;
	private String tagline;
	private boolean adult;
	private String homepage;
	private String status;

	private boolean isFavoutite;

	public boolean isFavoutite() {
		return isFavoutite;
	}

	public void setFavoutite(boolean favoutite) {
		isFavoutite = favoutite;
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

	public List<GenresItem> getGenre(){
		return genres;
	}

	public Object getPopularity(){
		return popularity;
	}

	public List<ProductionCountriesItem> getProductionCountries(){
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
		return AppConfig.Companion.BASE_IMAGE + posterPath;
	}

	public List<SpokenLanguagesItem> getSpokenLanguages(){
		return spokenLanguages;
	}

	public List<ProductionCompaniesItem> getProductionCompanies(){
		return productionCompanies;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public Object getVoteAverage(){
		return voteAverage;
	}

	public BelongsToCollection getBelongsToCollection(){
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

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public void setGenres(List<GenresItem> genres) {
		this.genres = genres;
	}

	public void setPopularity(Object popularity) {
		this.popularity = popularity;
	}

	public void setProductionCountries(List<ProductionCountriesItem> productionCountries) {
		this.productionCountries = productionCountries;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public void setSpokenLanguages(List<SpokenLanguagesItem> spokenLanguages) {
		this.spokenLanguages = spokenLanguages;
	}

	public void setProductionCompanies(List<ProductionCompaniesItem> productionCompanies) {
		this.productionCompanies = productionCompanies;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setVoteAverage(Object voteAverage) {
		this.voteAverage = voteAverage;
	}

	public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
		this.belongsToCollection = belongsToCollection;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MovieDetailResponse() {
	}

	public MovieDetailResponse(String originalLanguage, String imdbId, boolean video, String title, String backdropPath, int revenue, List<GenresItem> genres, Object popularity, List<ProductionCountriesItem> productionCountries, int id, int voteCount, int budget, String overview, String originalTitle, int runtime, String posterPath, List<SpokenLanguagesItem> spokenLanguages, List<ProductionCompaniesItem> productionCompanies, String releaseDate, Object voteAverage, BelongsToCollection belongsToCollection, String tagline, boolean adult, String homepage, String status, boolean isFavoutite) {
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
		this.isFavoutite = isFavoutite;
	}

	@Override
	public String toString() {
		return "MovieDetailResponse{" +
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
				", isFavoutite=" + isFavoutite +
				'}';
	}
}