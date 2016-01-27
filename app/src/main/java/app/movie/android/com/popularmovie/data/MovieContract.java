package app.movie.android.com.popularmovie.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

import app.movie.android.com.popularmovie.model.MovieDTO;
import app.movie.android.com.popularmovie.model.MovieReviewDTO;
import app.movie.android.com.popularmovie.model.MovieVideosDTO;

/**
 * Created by Romany on 12/25/2015.
 */
public class MovieContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "app.movie.android.com.popularmovie";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_FAVORITE_MOVIES = "favorite_movie_detail";
    public static final String PATH_MOVIE_REVIEW = "review_movie";

    public static final String PATH_MOVIE_TAILER = "video_movie";

    public static final class FavoriteMovieEntry implements BaseColumns {
        ////////////

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE_MOVIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE_MOVIES;

        public static Uri getFavoriteMovieUri() {
            return CONTENT_URI.buildUpon().build();
        }

        /////////////////////////////////

        public static final String[] FAVORITE_MOVIE_COLUMNS = {
                // In this case the id needs to be fully qualified with a table name, since
                // the content provider joins the location & weather tables in the background
                // (both have an _id column)
                // On the one hand, that's annoying.  On the other, you can search the weather table
                // using the location set by the user, which is only in the Location table.
                // So the convenience is worth it.
                MovieContract.FavoriteMovieEntry.TABLE_NAME + "." + MovieContract.FavoriteMovieEntry._ID,
                MovieContract.FavoriteMovieEntry.COLUMN_IS_FAVORITE,
                MovieContract.FavoriteMovieEntry.COLUMN_TITLE,
                MovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW,
                MovieContract.FavoriteMovieEntry.COLUMN_POSTER_IMG_URL,
                MovieContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE,
                MovieContract.FavoriteMovieEntry.COLUMN_VOTE_RATE,
                MovieContract.FavoriteMovieEntry.COLUMN_BACKGROUND_IMG_URL
        };
        ////////////////
        public static final String TABLE_NAME = "favorite_movie_detail";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_IMG_URL = "poster_img_url";
        public static final String COLUMN_BACKGROUND_IMG_URL = "background_img_url";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_RATE = "vote_rate";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_IS_FAVORITE = "is_favorite";


        public static ContentValues createFavoriteMovieValuesByMovieDTO(MovieDTO movieDTO) {
            ContentValues movieValues = new ContentValues();
            movieValues.put(_ID, movieDTO.getId());
            movieValues.put(COLUMN_TITLE, movieDTO.getTitle());
            if (null != movieDTO.getPosterImagePath()) {
                movieValues.put(COLUMN_POSTER_IMG_URL, movieDTO.getPosterImagePath());
            }
            if (null != movieDTO.getBackImagePath()) {
                movieValues.put(COLUMN_BACKGROUND_IMG_URL, movieDTO.getBackImagePath());
            }
            movieValues.put(COLUMN_VOTE_RATE, movieDTO.getVoteRate());
            movieValues.put(COLUMN_OVERVIEW, movieDTO.getOverView());

            movieValues.put(COLUMN_RELEASE_DATE, movieDTO.getReleaseDate());
            movieValues.put(COLUMN_IS_FAVORITE, 1);
            return movieValues;
        }

        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ReviewMovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_REVIEW).build();
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_REVIEW;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_REVIEW;

        public static Uri getMovieReviewUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String[] MOVIE_REVIEW_COLUMNS = {
                MovieContract.ReviewMovieEntry.TABLE_NAME + "." + MovieContract.ReviewMovieEntry._ID,
                MovieContract.ReviewMovieEntry.COLUMN_AUTHOR,
                MovieContract.ReviewMovieEntry.COLUMN_CONTENT,
                MovieContract.ReviewMovieEntry.COLUMN_MOVIE_ID
        };
        //////////
        public static final String TABLE_NAME = "review_movie";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static ContentValues createReviewMovieValuesByReviewMovie(long movieId, MovieReviewDTO reviewDTO) {
            ContentValues reviewMovieValues = new ContentValues();
            reviewMovieValues.put(_ID, reviewDTO.getId());
            reviewMovieValues.put(COLUMN_AUTHOR, reviewDTO.getAuthor());
            reviewMovieValues.put(COLUMN_CONTENT, reviewDTO.getContent());
            reviewMovieValues.put(COLUMN_MOVIE_ID, movieId);
            return reviewMovieValues;
        }


    }

    public static final class VideoMovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_TAILER).build();
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_TAILER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_TAILER;

        public static Uri getMovieTrailerUri() {
            return CONTENT_URI.buildUpon().build();
        }

        /////////////////
        public static String[] MOVIE_TRAILER_COLUMNS = {
                MovieContract.VideoMovieEntry.TABLE_NAME + "." + MovieContract.VideoMovieEntry._ID,
                MovieContract.VideoMovieEntry.COLUMN_KEY,
                MovieContract.VideoMovieEntry.COLUMN_NAME,
                MovieContract.VideoMovieEntry.COLUMN_MOVIE_ID
        };
        public static final String TABLE_NAME = "video_movie";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static ContentValues createVideoMovieValuesByReviewMovie(long movieId, MovieVideosDTO video) {
            ContentValues videoMovieValues = new ContentValues();
            videoMovieValues.put(_ID, video.getId());
            videoMovieValues.put(COLUMN_KEY, video.getKey());
            videoMovieValues.put(COLUMN_NAME, video.getName());
            videoMovieValues.put(COLUMN_MOVIE_ID, movieId);
            return videoMovieValues;
        }

    }
}

