package app.movie.android.com.popularmovie.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Romany on 12/25/2015.
 */
@SuppressWarnings("serial")
public class MovieVideosDTO implements Serializable {
    public static final String JSON_VIDEO_ID = "id";
    public static final String JSON_VIDEO_NAME = "name";
    public static final String JSON_VIDEO_KEY = "key";
    public static final String JSON_VIDEO_TYPE = "type";
    @SerializedName(JSON_VIDEO_ID)
    private String id;
    @SerializedName(JSON_VIDEO_NAME)
    private String name;
    @SerializedName(JSON_VIDEO_KEY)
    private String key;
    @SerializedName(JSON_VIDEO_TYPE)
    private String type;
    private int movieId;

    public MovieVideosDTO() {

    }


    public String getKey() {
        return key;
    }


    public String getName() {
        return name;
    }


    public int getMovieId() {
        return movieId;
    }


    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
