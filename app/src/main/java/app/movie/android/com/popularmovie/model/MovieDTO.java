package app.movie.android.com.popularmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Romany on 12/5/2015.
 */

@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class MovieDTO implements Parcelable {
    private final String LOG_TAG = MovieDTO.class.getSimpleName();
    public static final String MOVIE_DETAIL_INTENT_KEY = "MOVIE_DETAIL";
    public final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    public final static String RECOMMENDED_IMAGE_SIZE = "w185";
    public static final String JSON_MOVIE_ID = "id";
    public static final String JSON_MOVIE_OVERVIEW = "overview";
    public static final String JSON_MOVIE_RELEASE_DATE = "release_date";
    public static final String JSON_MOVIE_TITLE = "title";
    public static final String JSON_MOVIE_POSTER_PATH = "poster_path";
    public static final String JSON_MOVIE_BACK_PATH = "backdrop_path";
    public static final String JSON_MOVIE_VOTE_AVG = "vote_average";
    public static final String JSON_MOVIE_VOTE_COUNT = "vote_count";
    @SerializedName(JSON_MOVIE_ID)
    private int id;
    @SerializedName(JSON_MOVIE_TITLE)
    private String title;
    @SerializedName(JSON_MOVIE_BACK_PATH)
    private String backImagePath;
    @SerializedName(JSON_MOVIE_POSTER_PATH)
    private String posterImagePath;
    @SerializedName(JSON_MOVIE_RELEASE_DATE)
    private String releaseDate;
    @SerializedName(JSON_MOVIE_OVERVIEW)
    private String overView;
    @SerializedName(JSON_MOVIE_VOTE_AVG)
    private double voteRate;
    @SerializedName(JSON_MOVIE_VOTE_COUNT)
    private int voteCount;

    private boolean favorite;
    private ArrayList<MovieReviewDTO> movieReviews;
    private ArrayList<MovieVideosDTO> movieVideos;

    public MovieDTO() {
        this.favorite = false;
    }


    public int getVoteCount() {
        return voteCount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getBackImagePath() {
        if (null == this.backImagePath || !this.backImagePath.contains((IMAGE_BASE_URL))) {
            this.backImagePath = IMAGE_BASE_URL + RECOMMENDED_IMAGE_SIZE + this.backImagePath;
        }
        return this.backImagePath;
    }

    public String getPosterImagePath() {
        if (null == this.posterImagePath || !this.posterImagePath.contains((IMAGE_BASE_URL))) {
            this.posterImagePath = IMAGE_BASE_URL + RECOMMENDED_IMAGE_SIZE + this.posterImagePath;
        }

        return posterImagePath;
    }

    public void setBackImagePath(String backImagePath) {

        this.backImagePath = backImagePath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }


    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public void setVoteRate(double voteRate) {
        this.voteRate = voteRate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(int favoriteValue) {
        this.favorite = (favoriteValue == 1);
    }

    public String getOverView() {
        return overView;
    }


    public String getReleaseDate() {
        return releaseDate;
    }


    public double getVoteRate() {
        return voteRate;
    }


    public ArrayList<MovieReviewDTO> getMovieReviews() {
        return movieReviews;
    }


    public ArrayList<MovieVideosDTO> getMovieVideos() {
        return movieVideos;
    }

    public void setMovieReviews(ArrayList<MovieReviewDTO> movieReviews) {
        this.movieReviews = movieReviews;
    }

    public void setMovieVideos(ArrayList<MovieVideosDTO> movieVideos) {
        this.movieVideos = movieVideos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
    private int id;
        @SerializedName(JSON_MOVIE_TITLE)
        private String title;
        @SerializedName(JSON_MOVIE_BACK_PATH)
        private String backImagePath;
        @SerializedName(JSON_MOVIE_POSTER_PATH)
        private String posterImagePath;
        @SerializedName(JSON_MOVIE_RELEASE_DATE)
        private String releaseDate;
        @SerializedName(JSON_MOVIE_OVERVIEW)
        private String overView;
        @SerializedName(JSON_MOVIE_VOTE_AVG)
        private double voteRate;
        @SerializedName(JSON_MOVIE_VOTE_COUNT)
        private int voteCount;

        private boolean favorite;
        private ArrayList<MovieReviewDTO> movieReviews;
        private ArrayList<MovieVideosDTO> movieVideos;
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(backImagePath);
        dest.writeString(posterImagePath);
        dest.writeString(releaseDate);
        dest.writeString(overView);
        dest.writeInt(voteCount);
        dest.writeDouble(voteRate);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeTypedList(movieReviews);
        dest.writeTypedList(movieVideos);

    }

    public MovieDTO(Parcel in) {
        id = in.readInt();
        title = in.readString();
        backImagePath = in.readString();
        posterImagePath = in.readString();
        releaseDate = in.readString();
        overView = in.readString();
        voteCount = in.readInt();
        voteRate = in.readDouble();
        favorite = (in.readByte() == 1) ? true : false;
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<MovieDTO> CREATOR
            = new Parcelable.Creator<MovieDTO>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public MovieDTO createFromParcel(Parcel in) {
            return new MovieDTO(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public MovieDTO[] newArray(int size) {
            return new MovieDTO[size];
        }
    };
}
