package app.movie.android.com.popularmovie.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Romany on 1/26/2016.
 */
public class MovieProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper movieDbHelper;

    static final int FAVORITE_MOVIES = 100;
    static final int MOVIE_REVIEW = 102;
    static final int MOVIE_TRAILER = 104;

    private static final SQLiteQueryBuilder sMovieBySettingQueryBuilder;

    static {
        sMovieBySettingQueryBuilder = new SQLiteQueryBuilder();

    }


    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case FAVORITE_MOVIES:
                return MovieContract.FavoriteMovieEntry.CONTENT_LIST_TYPE;

            case MOVIE_REVIEW:
                return MovieContract.ReviewMovieEntry.CONTENT_LIST_TYPE;
            case MOVIE_TRAILER:
                return MovieContract.VideoMovieEntry.CONTENT_LIST_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // and query the database accordingly.

        System.out.print("in query in content provider");
        Cursor retCursor;
        int x = sUriMatcher.match(uri);
        Log.i("COntent provider query", "=" + x);
        switch (sUriMatcher.match(uri)) {

            case FAVORITE_MOVIES: {
                retCursor = getAllFavoriteMovies(uri, projection, sortOrder);
                break;
            }
            case MOVIE_REVIEW:
                retCursor = getMovieReviews(uri, projection, selection, selectionArgs, sortOrder);
                break;
            case MOVIE_TRAILER:
                retCursor = getMovieTrailer(uri, projection, selection, selectionArgs, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    private Cursor getMovieTrailer(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        System.out.println("on getMovieTrailer");
        sMovieBySettingQueryBuilder.setTables(MovieContract.VideoMovieEntry.TABLE_NAME);
        return sMovieBySettingQueryBuilder.query(movieDbHelper.getReadableDatabase(),
                projection,
                selection, selectionArgs,
                null,
                null,
                "_ID"
        );

    }

    private Cursor getMovieReviews(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        System.out.println("on getMovieReviews");
        sMovieBySettingQueryBuilder.setTables(MovieContract.ReviewMovieEntry.TABLE_NAME);
        return sMovieBySettingQueryBuilder.query(movieDbHelper.getReadableDatabase(),
                projection,
                selection, selectionArgs,
                null,
                null,
                "_ID"
        );
    }

    private Cursor getAllFavoriteMovies(Uri uri, String[] projection, String sortOrder) {

        sMovieBySettingQueryBuilder.setTables(MovieContract.FavoriteMovieEntry.TABLE_NAME);
        return sMovieBySettingQueryBuilder.query(movieDbHelper.getReadableDatabase(),
                projection,
                null, null,
                null,
                null,
                "_ID"
        );
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FAVORITE_MOVIES: {

                long _id = db.insert(MovieContract.FavoriteMovieEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.FavoriteMovieEntry.buildWeatherUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = 0;
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case FAVORITE_MOVIES:
                rowsDeleted = db.delete(MovieContract.FavoriteMovieEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case MOVIE_REVIEW:
                rowsDeleted = db.delete(MovieContract.ReviewMovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_TRAILER:
                rowsDeleted = db.delete(MovieContract.VideoMovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case FAVORITE_MOVIES:
                db.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.FavoriteMovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;


            case MOVIE_REVIEW:
                db.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.ReviewMovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case MOVIE_TRAILER:
                db.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MovieContract.VideoMovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;
        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MovieContract.PATH_FAVORITE_MOVIES, FAVORITE_MOVIES);
        matcher.addURI(authority, MovieContract.PATH_MOVIE_REVIEW, MOVIE_REVIEW);
        matcher.addURI(authority, MovieContract.PATH_MOVIE_TAILER, MOVIE_TRAILER);
        return matcher;
    }
}
