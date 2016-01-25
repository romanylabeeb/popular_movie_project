package app.movie.android.com.popularmovie.activities.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.model.MovieDTO;

public class DetailsActivity extends Activity {
    public static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            Intent movieDetailIntent = this.getIntent();
            DetailsFragment df = new DetailsFragment();
            df.setMovieDto((MovieDTO) movieDetailIntent.getSerializableExtra(MovieDTO.MOVIE_DETAIL_INTENT_KEY));
            getFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, df)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}