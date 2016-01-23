package app.movie.android.com.popularmovie.activities.details;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.activities.main.MainActivity;
import app.movie.android.com.popularmovie.asynctasks.BaseFetchMovieTask;
import app.movie.android.com.popularmovie.asynctasks.FetchMovieReviewsTask;
import app.movie.android.com.popularmovie.asynctasks.FetchMovieVideosTask;
import app.movie.android.com.popularmovie.customizedadapter.MovieBaseAdapter;
import app.movie.android.com.popularmovie.customizedadapter.MovieReviewAdapter;
import app.movie.android.com.popularmovie.customizedadapter.MovieVideoAdapter;
import app.movie.android.com.popularmovie.customizelist.ExpandableHeightListView;
import app.movie.android.com.popularmovie.model.MovieDTO;
import app.movie.android.com.popularmovie.model.MovieVideosDTO;

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}