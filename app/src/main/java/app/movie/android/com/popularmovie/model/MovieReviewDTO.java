package app.movie.android.com.popularmovie.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Romany on 12/25/2015.
 */

public class MovieReviewDTO implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(author);
    }

    public MovieReviewDTO(Parcel in) {
        movieId=in.readInt();
        id=in.readString();
        content=in.readString();
        author=in.readString();
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<MovieReviewDTO> CREATOR
            = new Parcelable.Creator<MovieReviewDTO>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public MovieReviewDTO createFromParcel(Parcel in) {
            return new MovieReviewDTO(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public MovieReviewDTO[] newArray(int size) {
            return new MovieReviewDTO[size];
        }
    };
}
