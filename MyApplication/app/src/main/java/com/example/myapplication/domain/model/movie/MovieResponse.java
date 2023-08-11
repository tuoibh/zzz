package com.example.myapplication.domain.model.movie;

import com.example.myapplication.data.model.movie.MovieResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import java.util.List;

public class MovieResponse {
    private int page;
    private int totalPages;
    private List<MovieResult> results;
    private int totalResults;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<MovieResult> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public MovieResponse() {
    }

    public MovieResponse(int page, int totalPages, List<MovieResult> results, int totalResults) {
        this.page = page;
        this.totalPages = totalPages;
        this.results = results;
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "Response{" + "page = '" + page + '\'' + ",total_pages = '" + totalPages + '\'' + ",results = '" + results + '\'' + ",total_results = '" + totalResults + '\'' + "}";
    }

    public MovieResponses transferFrom(MovieResponse mrp) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return modelMapper.map(mrp, MovieResponses.class);
    }

}