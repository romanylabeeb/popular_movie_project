package app.movie.android.com.popularmovie.customizedadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.activities.main.MainActivity;
import app.movie.android.com.popularmovie.activities.main.PopularMoviesFragment;
import app.movie.android.com.popularmovie.model.MovieDTO;

/**
 * Created by Romany on 12/5/2015.
 */
public class MoviesAdapter extends MovieBaseAdapter {

    private int selectedPosition;

    public MoviesAdapter(Context context, final int layoutResourceId, final ArrayList<Object> dtos) {
        super(context, layoutResourceId, dtos);

    }

    @Override
    protected void setViewHolderValuesByDTO(Object viewHolder, Object dto) {
        MovieHolder movieHolder = (MovieHolder) viewHolder;
        MovieDTO movie = (MovieDTO) dto;
        // movieHolder.txtMovieTitle.setText(movie.getTitle());
        Picasso.with(context).load(movie.getPosterImagePath())
                .fit()
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.movie_placeholder_error)
                .into(movieHolder.imgMovie);
        if (movie.isFavorite()) {
            movieHolder.txtMovieTitle.setBackgroundResource(R.drawable.remove);
        } else {
            movieHolder.txtMovieTitle.setBackgroundResource(R.drawable.add);
        }
        // here's our onTouchListener


    }

    @Override
    protected Object getViewHolderObjectByView(View row) {
        MovieHolder viewHolder = new MovieHolder();
        viewHolder.txtMovieTitle = (TextView) row.findViewById(R.id.txtMovieTitle);
        viewHolder.imgMovie = (ImageView) row.findViewById(R.id.imgMovie);
        //viewHolder.btnFavouriteMovie = (Button) row.findViewById(R.id.btnFavouriteMovie_in_list);
        return viewHolder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (MainActivity.ismTwoPane() && convertView != null) {
            if (position == selectedPosition) {
                convertView.setBackgroundColor(Color.parseColor("#105d7a"));
                //  view.invalidate();
            } else {
                convertView.setBackgroundColor(0x00000000);
            }
        }
        return super.getView(position, convertView, parent);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public Object getItem(int position) {
        return dtos.get(position);
    }

    public static class MovieHolder {
        public ImageView imgMovie;
        public TextView txtMovieTitle;
        //Button btnFavouriteMovie;
    }


}
