package app.movie.android.com.popularmovie.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.test.AndroidTestCase;

import java.util.HashSet;
import app.movie.android.com.popularmovie.data.*;
/**
 * Created by Romany on 12/26/2015.
 */
public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();



    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */
    public void setUp() {
        deleteTheDatabase();

    }
    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }
    //define tables name in hashset
    private HashSet<String> getHashSetTablesName() {
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(MovieContract.FavoriteMovieEntry.TABLE_NAME);
        tableNameHashSet.add(MovieContract.ReviewMovieEntry.TABLE_NAME);
        tableNameHashSet.add(MovieContract.VideoMovieEntry.TABLE_NAME);
        return tableNameHashSet;
    }

//define movie database
    private SQLiteDatabase getSLiteDatabase(){
        return   new MovieDbHelper(
                this.mContext).getWritableDatabase();
    }

    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)

        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
        SQLiteDatabase db=this.getSLiteDatabase();
        //here database is opened
        assertEquals(true, db.isOpen());



//        // Build a HashSet of all of the column names we want to look for
//        final HashSet<String> locationColumnHashSet = new HashSet<String>();
//        locationColumnHashSet.add(WeatherContract.LocationEntry._ID);
//        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_CITY_NAME);
//        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_COORD_LAT);
//        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_COORD_LONG);
//        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING);
//
//        int columnNameIndex = c.getColumnIndex("name");
//        do {
//            String columnName = c.getString(columnNameIndex);
//            locationColumnHashSet.remove(columnName);
//        } while(c.moveToNext());
//
//        // if this fails, it means that your database doesn't contain all of the required location
//        // entry columns
//        assertTrue("Error: The database doesn't contain all of the required location entry columns",
//                locationColumnHashSet.isEmpty());
        db.close();
    }
public  void testCreatedTables()throws Throwable {
    final HashSet<String> tableNameHashSet =this.getHashSetTablesName();
    SQLiteDatabase db=this.getSLiteDatabase();

    // have we created the tables we want?
    Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

    assertTrue("Error: This means that the database has not been created correctly",
            c.moveToFirst());

    // verify that the tables have been created
    do {
        System.out.println("tab name="+c.getString(0));

        tableNameHashSet.remove(c.getString(0));
    } while( c.moveToNext() );

 //if tableNameHashSet not empty there is an error on tables
    assertTrue("Error: Your database was created without both the favourite movie and reviews and videos entry tables",
            tableNameHashSet.isEmpty());

    // now, do our tables contain the correct columns?
    c = db.rawQuery("PRAGMA table_info(" + MovieContract.ReviewMovieEntry.TABLE_NAME + ")",
            null);

    assertTrue("Error: This means that we were unable to query the database for table ReviewMovieEntry.",
            c.moveToFirst());

    // now, do our tables contain the correct columns in video table?
    c = db.rawQuery("PRAGMA table_info(" + MovieContract.VideoMovieEntry.TABLE_NAME + ")",
            null);

    assertTrue("Error: This means that we were unable to query the database for table VideoMovieEntry.",
            c.moveToFirst());

    // now, do our tables contain the correct columns in video table?
    c = db.rawQuery("PRAGMA table_info(" + MovieContract.FavoriteMovieEntry.TABLE_NAME + ")",
            null);

    assertTrue("Error: This means that we were unable to query the database for table FavouriteMovieEntry.",
            c.moveToFirst());
}

}
