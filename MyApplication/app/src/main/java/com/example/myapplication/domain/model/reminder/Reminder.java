package com.example.myapplication.domain.model.reminder;

public class Reminder {
    private int movieId;
    private String movieName;
    private String posterUrl;
    private String voteAverage;
    private String dateReminder;
    private String timeReminder;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getDateReminder() {
        return dateReminder;
    }

    public void setDateReminder(String dateReminder) {
        this.dateReminder = dateReminder;
    }

    public String getTimeReminder() {
        return timeReminder;
    }

    public void setTimeReminder(String timeReminder) {
        this.timeReminder = timeReminder;
    }

    public Reminder() {
    }

    public Reminder(int movieId, String movieName, String posterUrl, String voteAverage, String dateReminder, String timeReminder) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.posterUrl = posterUrl;
        this.voteAverage = voteAverage;
        this.dateReminder = dateReminder;
        this.timeReminder = timeReminder;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", dateReminder='" + dateReminder + '\'' +
                ", timeReminder='" + timeReminder + '\'' +
                '}';
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
