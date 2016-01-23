package app.movie.android.com.popularmovie.customizedadapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.model.MovieDTO;

/**
 * Created by Romany on 12/5/2015.
 */
public class MoviesAdapter extends MovieBaseAdapter {


    public MoviesAdapter(Context context, final int layoutResourceId, final ArrayList<Object> dtos) {
        super(context, layoutResourceId, dtos);

    }

    @Override
    protected void setViewHolderValuesByDTO(Object viewHolder, Object dto) {
        MovieHolder movieHolder = (MovieHolder) viewHolder;
        MovieDTO movie = (MovieDTO) dto;
        // movieHolder.txtMovieTitle.setText(movie.getTitle());
        Picasso.with(context).load(movie.getPosterImagePath())
                .fit() // will explain later
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


    public static class MovieHolder {
        public ImageView imgMovie;
        public TextView txtMovieTitle;
        //Button btnFavouriteMovie;
    }

    @Override
    public int getViewTypeCount() {
        System.out.println("getViewTypeCount=");
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return dtos.get(position);
    }

    @Override
    public int getCount() {
        //getCount() represents how many items are in the list
        return dtos.size();
    }


    @Override
    //get the position id of the item from the list
    public long getItemId(int i) {
        System.out.println("itemId=" + i);
        return 0;
    }


}
