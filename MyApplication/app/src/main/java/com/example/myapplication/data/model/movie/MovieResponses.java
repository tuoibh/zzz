package com.example.myapplication.data.model.movie;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponses {

    @SerializedName("page")
    @Expose
    private final int page;
    @SerializedName("total_pages")
    @Expose
    private final int totalPages;
    @SerializedName("results")
    @Expose
    private final List<MovieResults> results;
    @SerializedName("total_results")
    @Expose
    private final int totalResults;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<MovieResults> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public MovieResponses(int page, int totalPages, List<MovieResults> results, int totalResults) {
        this.page = page;
        this.totalPages = totalPages;
        this.results = results;
        this.totalResults = totalResults;
    }

    @NonNull
    @Override
    public String toString() {
        return "Response{" +
            "page = '" + page + '\'' +
            ",total_pages = '" + totalPages + '\'' +
            ",results = '" + results + '\'' +
            ",total_results = '" + totalResults + '\'' +
            "}";
    }
}