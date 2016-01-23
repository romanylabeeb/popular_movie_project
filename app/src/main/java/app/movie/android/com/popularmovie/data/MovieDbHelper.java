package app.movie.android.com.popularmovie.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import app.movie.android.com.popularmovie.data.MovieContract.FavoriteMovieEntry;
import app.movie.android.com.popularmovie.data.MovieContract.ReviewMovieEntry;
import app.movie.android.com.popularmovie.data.MovieContract.VideoMovieEntry;
import app.movie.android.com.popularmovie.model.MovieDTO;
import app.movie.android.com.popularmovie.model.MovieReviewDTO;
import app.movie.android.com.popularmovie.model.MovieVideosDTO;

/**
 * Created by Romany on 12/26/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 5;

    public static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.createFavoriteMovieTable(sqLiteDatabase);
        this.createReviewMovieTable(sqLiteDatabase);
        this.createVideoMovieTable(sqLiteDatabase);
        Log.i("dtatabase is created", "ddf");
    }

    private void createFavoriteMovieTable(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.FavoriteMovieEntry.TABLE_NAME + " (" +
                FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_POSTER_IMG_URL + " TEXT , " +
                FavoriteMovieEntry.COLUMN_BACKGROUND_IMG_URL + " TEXT , " +
                FavoriteMovieEntry.COLUMN_VOTE_RATE + " REAL  , " +
                FavoriteMovieEntry.COLUMN_IS_FAVORITE + " INTEGER  , " +
                FavoriteMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL); ";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }


    private void createReviewMovieTable(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_REVIEW_MOVIE_TABLE = "CREATE TABLE " + ReviewMovieEntry.TABLE_NAME + " (" +
                ReviewMovieEntry._ID + " TEXT PRIMARY KEY ," +
                ReviewMovieEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                ReviewMovieEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                ReviewMovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                " FOREIGN KEY (" + ReviewMovieEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                FavoriteMovieEntry.TABLE_NAME + " (" + FavoriteMovieEntry._ID + ")" +
                " ); ";
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_MOVIE_TABLE);
    }

    private void createVideoMovieTable(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_VIDEO_MOVIE_TABLE = "CREATE TABLE " + VideoMovieEntry.TABLE_NAME + " (" +
                VideoMovieEntry._ID + " TEXT PRIMARY KEY ," +
                VideoMovieEntry.COLUMN_KEY + " TEXT NOT NULL, " +
                VideoMovieEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                VideoMovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                " FOREIGN KEY (" + VideoMovieEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                FavoriteMovieEntry.TABLE_NAME + " (" + FavoriteMovieEntry._ID + ")" +
                " ); ";
        sqLiteDatabase.execSQL(SQL_CREATE_VIDEO_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavoriteMovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.ReviewMovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.VideoMovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean isFavouriteMovie(int movieId) {
        boolean found = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(("SELECT " + FavoriteMovieEntry._ID + " FROM " + FavoriteMovieEntry.TABLE_NAME +
                " WHERE " + FavoriteMovieEntry._ID + "=" + movieId), null);
        if (c.moveToFirst()) {
            found = true;
        }
        c.close();
        db.close();
        return found;
    }

    public ArrayList<MovieDTO> getAllFavoriteMovies() {
        ArrayList<MovieDTO> favoriteMovieDtos = new ArrayList<MovieDTO>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(("SELECT *" + " FROM " + FavoriteMovieEntry.TABLE_NAME), null);
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    MovieDTO movieDTO = new MovieDTO();
                    // Get version from Cursor
                    movieDTO.setId(cursor.getInt(cursor.getColumnIndex(FavoriteMovieEntry._ID)));
                    movieDTO.setPosterImagePath(cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_POSTER_IMG_URL)));
                    movieDTO.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_TITLE)));
                    movieDTO.setBackImagePath(cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_BACKGROUND_IMG_URL)));
                    movieDTO.setReleaseDate(cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_RELEASE_DATE)));
                    movieDTO.setOverView(cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_OVERVIEW)));
                    movieDTO.setVoteRate(cursor.getDouble(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_VOTE_RATE)));
                    movieDTO.setFavorite(cursor.getInt(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_IS_FAVORITE)));
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
        db.close();
        return favoriteMovieDtos;
    }

    public ArrayList<MovieReviewDTO> getFavoriteMovieReviewsList(int movieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MovieReviewDTO> favoriteMovieReviewsList = new ArrayList<MovieReviewDTO>();
        Cursor cursor = db.rawQuery(("SELECT *" + " FROM " + ReviewMovieEntry.TABLE_NAME + " WHERE " + ReviewMovieEntry.COLUMN_MOVIE_ID + "=" + movieId), null);
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    MovieReviewDTO reviewDto = new MovieReviewDTO();
                    reviewDto.setId(cursor.getString(cursor.getColumnIndex(ReviewMovieEntry._ID)));
                    reviewDto.setAuthor(cursor.getString(cursor.getColumnIndex(ReviewMovieEntry.COLUMN_AUTHOR)));
                    reviewDto.setContent(cursor.getString(cursor.getColumnIndex(ReviewMovieEntry.COLUMN_CONTENT)));
                    reviewDto.setMovieId(cursor.getInt(cursor.getColumnIndex(ReviewMovieEntry.COLUMN_MOVIE_ID)));
                    favoriteMovieReviewsList.add(reviewDto);
                    // move to next row
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();
        return favoriteMovieReviewsList;
    }

    public ArrayList<MovieVideosDTO> getFavoriteMovieVideosList(int movieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MovieVideosDTO> favoriteMovieVideosList = new ArrayList<MovieVideosDTO>();
        Cursor cursor = db.rawQuery(("SELECT *" + " FROM " + VideoMovieEntry.TABLE_NAME + " WHERE " + VideoMovieEntry.COLUMN_MOVIE_ID + "=" + movieId), null);
        if (cursor != null) {
            // move cursor to first row
            if (cursor.moveToFirst()) {
                do {
                    MovieVideosDTO videoDto = new MovieVideosDTO();
                    videoDto.setId(cursor.getString(cursor.getColumnIndex(VideoMovieEntry._ID)));
                    videoDto.setKey(cursor.getString(cursor.getColumnIndex(VideoMovieEntry.COLUMN_KEY)));
                    videoDto.setName(cursor.getString(cursor.getColumnIndex(VideoMovieEntry.COLUMN_KEY)));
                    videoDto.setMovieId(cursor.getInt(cursor.getColumnIndex(VideoMovieEntry.COLUMN_MOVIE_ID)));
                    favoriteMovieVideosList.add(videoDto);
                    // move to next row
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return favoriteMovieVideosList;
    }

    public void deleteFavouriteMovie(int movieId) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        int i1 = db.delete(ReviewMovieEntry.TABLE_NAME, ReviewMovieEntry.COLUMN_MOVIE_ID + "=" + movieId, null);
        int i2 = db.delete(VideoMovieEntry.TABLE_NAME, VideoMovieEntry.COLUMN_MOVIE_ID + "=" + movieId, null);
        int i3 = db.delete(FavoriteMovieEntry.TABLE_NAME, FavoriteMovieEntry._ID + "=" + movieId, null);
        Log.i("on delete i1=" + i1 + ",i2=" + i2 + ", i3=" + i3, "");
        db.close();
    }

    public void makeMovieAsFovourite(MovieDTO movieDetail) {
// Gets the data repository in write mode
        // Create a new map of values, where column names are the keys
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = FavoriteMovieEntry.createFavoriteMovieValuesByMovieDTO(movieDetail);
// Insert the new row, returning the primary key value of the new row
        long movieRowId;
        movieRowId = db.insert(
                FavoriteMovieEntry.TABLE_NAME, null,
                values);
        Log.i("Favourite movie Id=" + movieRowId, "");
        this.createReviewsRowSByReviewDTOs(movieRowId, movieDetail.getMovieReviews(), db);
        this.createViedosRowsByVideoDTOs(movieRowId, movieDetail.getMovieVideos(), db);
        db.close();


    }

    private void createViedosRowsByVideoDTOs(long movieId, ArrayList<?> movieVideos, SQLiteDatabase db) {
        for (Object object : movieVideos) {
            MovieVideosDTO video = (MovieVideosDTO) object;
            ContentValues values = VideoMovieEntry.createVideoMovieValuesByReviewMovie(movieId, video);
            long newRowId = db.insert(
                    VideoMovieEntry.TABLE_NAME, null,
                    values);
            Log.i("Video Id=" + newRowId, "");
        }


    }

    private void createReviewsRowSByReviewDTOs(long movieId, ArrayList<?> movieReviews, SQLiteDatabase db) {
        for (Object objectDto : movieReviews) {
            MovieReviewDTO review = (MovieReviewDTO) objectDto;
            ContentValues values = ReviewMovieEntry.createReviewMovieValuesByReviewMovie(movieId, review);
            long newRowId = db.insert(
                    ReviewMovieEntry.TABLE_NAME, null,
                    values);
            Log.i("Review Id=" + newRowId, "");
        }

    }


}

