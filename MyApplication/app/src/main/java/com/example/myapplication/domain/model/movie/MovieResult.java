package com.example.myapplication.domain.model.movie;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.myapplication.core.AppConfig;

import java.util.List;

public class MovieResult implements Parcelable {
    private String overview;
    private String originalLanguage;
    private String originalTitle;
    private boolean video;
    private String title;
    private List<Integer> genreIds;
    private String posterPath;
    private String backdropPath;
    private String releaseDate;
    private String popularity;
    private String voteAverage;
    private int id;
    private boolean adult;
    private int voteCount;

    private boolean isFavourite;

    protected MovieResult(Parcel in) {
        overview = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        video = in.readByte() != 0;
        title = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        releaseDate = in.readString();
        popularity = in.readString();
        voteAverage = in.readString();
        id = in.readInt();
        adult = in.readByte() != 0;
        voteCount = in.readInt();
        isFavourite = in.readByte() != 0;
    }

    public static final Creator<MovieResult> CREATOR = new Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel in) {
            return new MovieResult(in);
        }

        @Override
        public MovieResult[] newArray(int size) {
            return new MovieResult[size];
        }
    };

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
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
        return AppConfig.Companion.BASE_IMAGE + posterPath;
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

    public MovieResult() {
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

    public MovieResult(String overview, String originalLanguage, String originalTitle, boolean video, String title, List<Integer> genreIds, String posterPath, String backdropPath, String releaseDate, String popularity, String voteAverage, int id, boolean adult, int voteCount) {
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
    }

    public MovieResult(String overview, String originalLanguage, String originalTitle, boolean video, String title, List<Integer> genreIds, String posterPath, String backdropPath, String releaseDate, String popularity, String voteAverage, int id, boolean adult, int voteCount, boolean isFavourite) {
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

    @Override
    public String toString() {
        return "MovieResult{" +
                "overview='" + overview + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", video=" + video +
                ", title='" + title + '\'' +
                ", genreIds=" + genreIds +
                ", posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", popularity='" + popularity + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", id=" + id +
                ", adult=" + adult +
                ", voteCount=" + voteCount +
                ", isFavourite=" + isFavourite +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(overview);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(video);
            dest.writeBoolean(adult);
            dest.writeBoolean(isFavourite);
        }
        dest.writeString(title);
        dest.writeArray(genreIds.toArray());
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(releaseDate);
        dest.writeString(popularity);
        dest.writeString(voteAverage);
        dest.writeInt(id);
        dest.writeInt(voteCount);
    }
}