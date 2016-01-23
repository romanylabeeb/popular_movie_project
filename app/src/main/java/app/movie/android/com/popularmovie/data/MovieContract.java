package app.movie.android.com.popularmovie.data;

import android.content.ContentValues;
import android.provider.BaseColumns;

import app.movie.android.com.popularmovie.model.MovieDTO;
import app.movie.android.com.popularmovie.model.MovieReviewDTO;
import app.movie.android.com.popularmovie.model.MovieVideosDTO;

/**
 * Created by Romany on 12/25/2015.
 */
public class MovieContract {
    public static final class FavoriteMovieEntry implements BaseColumns {
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
    }

    public static final class ReviewMovieEntry implements BaseColumns {
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

