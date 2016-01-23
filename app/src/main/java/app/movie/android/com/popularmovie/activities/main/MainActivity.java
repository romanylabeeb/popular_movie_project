package app.movie.android.com.popularmovie.activities.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.Utilities.Utility;
import app.movie.android.com.popularmovie.activities.details.DetailsActivity;
import app.movie.android.com.popularmovie.activities.details.DetailsFragment;
import app.movie.android.com.popularmovie.activities.settings.SettingsActivity;
import app.movie.android.com.popularmovie.data.MovieDbHelper;
import app.movie.android.com.popularmovie.model.MovieDTO;

public class MainActivity extends Activity implements PopularMoviesFragment.Callback {
    public static MovieDbHelper movieDBHelper;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private static String LOG_TAG = MainActivity.class.getSimpleName();
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDBHelper = new MovieDbHelper(getApplicationContext());
        setContentView(R.layout.activity_main);
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PopularMoviesFragment())
//                    .commit();
//
//        }
        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailsFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            //getFragmentManager().get
        }

        PopularMoviesFragment poulareFragment1 = ((PopularMoviesFragment) getFragmentManager()
                .findFragmentById(R.id.movies_container));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//steps call async class & excute
            Intent settingIntent = new Intent(this, SettingsActivity.class);
            settingIntent.putExtra(Intent.EXTRA_TEXT, "settings");
            // downloadIntent.setData(Uri.parse(fileUrl));
            startActivity(settingIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PopularMoviesFragment poulareFragment1 = ((PopularMoviesFragment) getFragmentManager()
                .findFragmentById(R.id.movies_container));
        if (null != poulareFragment1) {
            poulareFragment1.getMoviesDtoAndGetSortKeyFromSharedPreferences();

        }
        DetailsFragment df = (DetailsFragment) getFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);

        if (null != df) {
            df.loadMovieDetail();

        }


    }

    @Override
    public void onItemSelected(MovieDTO movieDTO) {
        if (mTwoPane) {
            Log.i(LOG_TAG, "onItemSelected");
            DetailsFragment fragment = new DetailsFragment();
            fragment.setMovieDto(movieDTO);
            //  fragment.setViewVisible();
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();


        } else {
            Intent detailIntent = new Intent(this, DetailsActivity.class);
            detailIntent.putExtra(movieDTO.MOVIE_DETAIL_INTENT_KEY, movieDTO);
            startActivity(detailIntent);
        }
    }
}
