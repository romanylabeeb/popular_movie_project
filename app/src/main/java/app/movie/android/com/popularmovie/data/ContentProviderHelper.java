package app.movie.android.com.popularmovie.data;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import app.movie.android.com.popularmovie.model.MovieDTO;
import app.movie.android.com.popularmovie.model.MovieReviewDTO;
import app.movie.android.com.popularmovie.model.MovieVideosDTO;

/**
 * Created by Romany on 1/26/2016.
 */
public class ContentProviderHelper {
    public static final String LOG_TAG = ContentProviderHelper.class.getSimpleName();

    //all favorite movies
    private static final String sFavoriteMoviesSelection =
            MovieContract.FavoriteMovieEntry.TABLE_NAME +
                    "." + MovieContract.FavoriteMovieEntry._ID + " =?";
    //favoriteMovie Reviews selection
    private static final String sMovieReviewSelection =
            MovieContract.ReviewMovieEntry.TABLE_NAME +
                    "." + MovieContract.ReviewMovieEntry.COLUMN_MOVIE_ID + " =?";
    //favoriteMovie Trailer selection
    private static final String sMovieTrailerSelection =
            MovieContract.VideoMovieEntry.TABLE_NAME +
                    "." + MovieContract.VideoMovieEntry.COLUMN_MOVIE_ID + " =?";

    public static ArrayList<MovieDTO> getAllFavoriteMoviesAsList(ContentResolver cr) {

        Log.i(LOG_TAG, "getAllFavoriteMoviesAsList from content provider ");
        ArrayList<MovieDTO> favoriteMovieDtos = new ArrayList<MovieDTO>();

        Uri favoriteMoviesUri = MovieContract.FavoriteMovieEntry.getFavoriteMovieUri();
        Cursor cursor = cr.query(favoriteMoviesUri, MovieContract.FavoriteMovieEntry.FAVORITE_MOVIE_COLUMNS, null, null, null);//(students, null, null, null, "name");
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    MovieDTO movieDTO = new MovieDTO();
                    // Get version from Cursor
                    movieDTO.setId(cursor.getInt(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry._ID)));
                    movieDTO.setPosterImagePath(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_POSTER_IMG_URL)));
                    movieDTO.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_TITLE)));
                    movieDTO.setBackImagePath(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_BACKGROUND_IMG_URL)));
                    movieDTO.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE)));
                    movieDTO.setOverView(cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW)));
                    movieDTO.setVoteRate(cursor.getDouble(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_VOTE_RATE)));
                    movieDTO.setFavorite(cursor.getInt(cursor.getColumnIndex(MovieContract.FavoriteMovieEntry.COLUMN_IS_FAVORITE)));
                    // add the bookName into the bookTitles ArrayList
                    //  movieDTO.setMovieReviews(getFavoriteMovieReviewsList(movieDTO.getId(), db));
                    //movieDTO.setMovieVideos(getFavoriteMovieVideosList(movieDTO.getId(), db));
                    // Object dto = movieDTO;
                    favoriteMovieDtos.add(movieDTO);
                    // move to next row
                } while (cursor.moveToNext());


            }
        }
        cursor.close();
        return favoriteMovieDtos;
    }

    public static ArrayList<MovieReviewDTO> getMovieReviewsById(ContentResolver contentResolver, int id) {
        Log.i(LOG_TAG, "getMovieReviewsById ");
        ArrayList<MovieReviewDTO> favoriteMovieReviewList = new ArrayList<MovieReviewDTO>();

        Uri movieReviewsUri = MovieContract.ReviewMovieEntry.getMovieReviewUri();
        Cursor cursor = contentResolver.query(movieReviewsUri, MovieContract.ReviewMovieEntry.MOVIE_REVIEW_COLUMNS,
                sMovieReviewSelection, new String[]{Integer.toString(id)}, null);
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    MovieReviewDTO reviewDto = new MovieReviewDTO();
                    reviewDto.setId(cursor.getString(cursor.getColumnIndex(MovieContract.ReviewMovieEntry._ID)));
                    reviewDto.setAuthor(cursor.getString(cursor.getColumnIndex(MovieContract.ReviewMovieEntry.COLUMN_AUTHOR)));
                    reviewDto.setContent(cursor.getString(cursor.getColumnIndex(MovieContract.ReviewMovieEntry.COLUMN_CONTENT)));
                    reviewDto.setMovieId(cursor.getInt(cursor.getColumnIndex(MovieContract.ReviewMovieEntry.COLUMN_MOVIE_ID)));
                    reviewDto.setMovieId(id);
                    favoriteMovieReviewList.add(reviewDto);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        return favoriteMovieReviewList;
    }

    public static ArrayList<MovieVideosDTO> getMovieTrailerById(ContentResolver contentResolver, int id) {
        ArrayList<MovieVideosDTO> favoriteMovieTrailerList = new ArrayList<MovieVideosDTO>();
        Uri movieVideoUri = MovieContract.VideoMovieEntry.getMovieTrailerUri();
        Cursor cursor = contentResolver.query(movieVideoUri, MovieContract.VideoMovieEntry.MOVIE_TRAILER_COLUMNS,
                sMovieTrailerSelection, new String[]{Integer.toString(id)}, null);
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    MovieVideosDTO videoDto = new MovieVideosDTO();
                    videoDto.setId(cursor.getString(cursor.getColumnIndex(MovieContract.VideoMovieEntry._ID)));
                    videoDto.setKey(cursor.getString(cursor.getColumnIndex(MovieContract.VideoMovieEntry.COLUMN_KEY)));
                    videoDto.setName(cursor.getString(cursor.getColumnIndex(MovieContract.VideoMovieEntry.COLUMN_KEY)));
                    videoDto.setMovieId(cursor.getInt(cursor.getColumnIndex(MovieContract.VideoMovieEntry.COLUMN_MOVIE_ID)));
                    favoriteMovieTrailerList.add(videoDto);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return favoriteMovieTrailerList;
    }

    public static void insertNewFavoriteMovie(ContentResolver contentResolver, MovieDTO movieDetail) {
        insertIntoFavoriteMovieTable(contentResolver, movieDetail);
        insertReviews(contentResolver, movieDetail.getId(), movieDetail.getMovieReviews());
        insertTrailers(contentResolver, movieDetail.getId(), movieDetail.getMovieVideos());
    }

    private static void insertReviews(ContentResolver contentResolver, int movieId, ArrayList<MovieReviewDTO> movieReviews) {
        Uri movieReviewsUri = MovieContract.ReviewMovieEntry.getMovieReviewUri();
        ContentValues[] reviewsValueList = new ContentValues[movieReviews.size()];

        // Create a set of insert ContentProviderOperations
        for (int i = 0; i < movieReviews.size(); i++) {
            reviewsValueList[i] = MovieContract.ReviewMovieEntry.createReviewMovieValuesByReviewMovie(movieId, movieReviews.get(i));
        }
        contentResolver.bulkInsert(movieReviewsUri, reviewsValueList);

    }

    private static void insertTrailers(ContentResolver contentResolver, int movieId, ArrayList<MovieVideosDTO> movieVideos) {
        Uri movieTrailersUri = MovieContract.VideoMovieEntry.getMovieTrailerUri();
        ContentValues[] trailerValueList = new ContentValues[movieVideos.size()];

        // Create a set of insert ContentProviderOperations
        for (int i = 0; i < movieVideos.size(); i++) {
            trailerValueList[i] = MovieContract.VideoMovieEntry.createVideoMovieValuesByReviewMovie(movieId, movieVideos.get(i));
        }
        contentResolver.bulkInsert(movieTrailersUri, trailerValueList);

    }

    private static void insertIntoFavoriteMovieTable(ContentResolver contentResolver, MovieDTO movieDetail) {
        ContentValues values = MovieContract.FavoriteMovieEntry.createFavoriteMovieValuesByMovieDTO(movieDetail);
        Uri favoriteMoviesUri = MovieContract.FavoriteMovieEntry.getFavoriteMovieUri();
        Uri mNewUri = contentResolver.insert(favoriteMoviesUri, values);
        // mNewUri.g

        System.out.println("mNewUri of fab table=" + mNewUri.getLastPathSegment());
    }

    public static void deleteFavoriteMovie(ContentResolver contentResolver, int movieId, int reviewsNumber, int trailerNumber) {
        deleteReviewsByMovieId(contentResolver, movieId, reviewsNumber);
        deleteTrailerByMovieId(contentResolver, movieId, trailerNumber);
        deleteFavoriteMovieByMovieId(contentResolver, movieId);
    }

    private static void deleteFavoriteMovieByMovieId(ContentResolver contentResolver, int movieId) {
        Uri favoriteMovieUri = MovieContract.FavoriteMovieEntry.getFavoriteMovieUri();
        int rowsDeleted = 0;
        rowsDeleted = contentResolver.delete(favoriteMovieUri,
                sFavoriteMoviesSelection, new String[]{Integer.toString(movieId)});
        if (1 != rowsDeleted) {
            Log.e(LOG_TAG, "Error on delete deleteFavoriteMovieByMovieId");
        } else {
            Log.i(LOG_TAG, "Done on delete deleteFavoriteMovieByMovieId");
        }
    }

    private static void deleteTrailerByMovieId(ContentResolver contentResolver, int movieId, int trailerNumber) {
        Uri movieTrailerUri = MovieContract.VideoMovieEntry.getMovieTrailerUri();
        int rowsDeleted = 0;
        rowsDeleted = contentResolver.delete(movieTrailerUri,
                sMovieTrailerSelection, new String[]{Integer.toString(movieId)});
        if (trailerNumber != rowsDeleted) {
            Log.e(LOG_TAG, "Error on delete deleteTrailerByMovieId");
        } else {
            Log.i(LOG_TAG, "Done on delete deleteTrailerByMovieId");
        }
    }

    private static void deleteReviewsByMovieId(ContentResolver contentResolver, int movieId, int reviewsNumber) {
        Uri movieReviewsUri = MovieContract.ReviewMovieEntry.getMovieReviewUri();
        int rowsDeleted = 0;
        rowsDeleted = contentResolver.delete(movieReviewsUri,   // the user dictionary content URI
                sMovieReviewSelection, new String[]{Integer.toString(movieId)});
        if (reviewsNumber != rowsDeleted) {
            Log.e(LOG_TAG, "Error on delete deleteReviewsByMovieId");
        } else {
            Log.i(LOG_TAG, "Done on delete deleteReviewsByMovieId");
        }
    }
}
