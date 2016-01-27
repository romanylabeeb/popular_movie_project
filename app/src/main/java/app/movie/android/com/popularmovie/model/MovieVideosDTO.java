package app.movie.android.com.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Romany on 12/25/2015.
 */

public class MovieVideosDTO implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(key);
        dest.writeString(type);
//TODO
    }

    public MovieVideosDTO(Parcel in) {
        //TODO
        movieId = in.readInt();
        id = in.readString();
        name = in.readString();
        key = in.readString();
        type = in.readString();


    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<MovieVideosDTO> CREATOR
            = new Parcelable.Creator<MovieVideosDTO>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public MovieVideosDTO createFromParcel(Parcel in) {
            return new MovieVideosDTO(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public MovieVideosDTO[] newArray(int size) {
            return new MovieVideosDTO[size];
        }
    };
}
