package app.movie.android.com.popularmovie.activities.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.activities.details.DetailsActivity;
import app.movie.android.com.popularmovie.activities.details.DetailsFragment;
import app.movie.android.com.popularmovie.activities.settings.SettingsActivity;
import app.movie.android.com.popularmovie.data.MovieContract;
import app.movie.android.com.popularmovie.data.MovieDbHelper;
import app.movie.android.com.popularmovie.model.MovieDTO;

public class MainActivity extends Activity implements PopularMoviesFragment.Callback {
    public static MovieDbHelper movieDBHelper;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private int rotation;
    private static String LOG_TAG = MainActivity.class.getSimpleName();
    private static boolean mTwoPane;
    private static Context context;
    private static final String SAVED_SORT_KEY = "SORT_KEY";

    public static boolean ismTwoPane() {
        return mTwoPane;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "in onCreate main");
        super.onCreate(savedInstanceState);
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            PopularMoviesFragment.SORT_KEY = savedInstanceState.getString(SAVED_SORT_KEY);
            Log.i(LOG_TAG, "in onCreate saved SORT_KEY=" + PopularMoviesFragment.SORT_KEY);
        }

        context = this;
        setContentView(R.layout.activity_main);
        movieDBHelper = new MovieDbHelper(getApplicationContext());

//        Picasso
//                .with(this)
//                .setIndicatorsEnabled(true);
        // StatsSnapshot picassoStats = Picasso.with(this).getSnapshot();
        //   Log.d("Picasso Stats", picassoStats.toString());
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailsFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingIntent = new Intent(this, SettingsActivity.class);
            settingIntent.putExtra(Intent.EXTRA_TEXT, "settings");
            startActivity(settingIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        Log.i(LOG_TAG, "in onResume main");
        super.onResume();
        this.resumePopularMoviesFragment();
        this.resumeDetailFragment();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(LOG_TAG, " onSaveInstanceState Pop fragment");
        Log.i(LOG_TAG, "in onSaveInstanceState  SORT_KEY=" + PopularMoviesFragment.SORT_KEY);
        savedInstanceState.putString(SAVED_SORT_KEY, PopularMoviesFragment.SORT_KEY);
        super.onSaveInstanceState(savedInstanceState);
    }


    private void resumeDetailFragment() {
        DetailsFragment df = (DetailsFragment) getFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
        if (null != df) {
            df.loadMovieDetail();
        }
    }

    private void resumePopularMoviesFragment() {
        Log.i(LOG_TAG, "in resumePopularMoviesFragment");
        PopularMoviesFragment popularMoviesFragment = ((PopularMoviesFragment) getFragmentManager()
                .findFragmentById(R.id.movies_container));
        if (null != popularMoviesFragment) {
            popularMoviesFragment.getMoviesDtoAndGetSortKeyFromSharedPreferences();

        }
    }

    @Override
    public void onItemSelected(MovieDTO movieDTO) {
        if (mTwoPane) {
            Log.i(LOG_TAG, "onItemSelected");
            this.showMovieDetailFragmentIn(movieDTO);
        } else {
            this.startDetailActivityUsingIntent(movieDTO);
        }
    }

    private void startDetailActivityUsingIntent(MovieDTO movieDTO) {
        Intent detailIntent = new Intent(this, DetailsActivity.class);
        detailIntent.putExtra(movieDTO.MOVIE_DETAIL_INTENT_KEY, movieDTO);
        startActivity(detailIntent);
    }

    private void showMovieDetailFragmentIn(MovieDTO movieDTO) {
        DetailsFragment fragment = new DetailsFragment();
        fragment.setMovieDto(movieDTO);
        getFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                .commit();
    }


}
