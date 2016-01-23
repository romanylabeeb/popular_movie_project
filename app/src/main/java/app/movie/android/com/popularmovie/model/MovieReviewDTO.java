package app.movie.android.com.popularmovie.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Romany on 12/25/2015.
 */
@SuppressWarnings("serial")
public class MovieReviewDTO implements Serializable {

    public static final String JSON_REVIEW_ID = "id";
    public static final String JSON_REVIEW_AUTHOR = "author";
    public static final String JSON_REVIEW_CONTENT = "content";
    @SerializedName(JSON_REVIEW_ID)
    private String id;
    @SerializedName(JSON_REVIEW_AUTHOR)
    private String author;
    @SerializedName(JSON_REVIEW_CONTENT)
    private String content;
    private int movieId;

    public MovieReviewDTO() {

    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
