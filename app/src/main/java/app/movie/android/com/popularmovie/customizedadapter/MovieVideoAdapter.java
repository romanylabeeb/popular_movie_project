package app.movie.android.com.popularmovie.customizedadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.model.MovieVideosDTO;

/**
 * Created by Romany on 12/26/2015.
 */
public class MovieVideoAdapter extends  MovieBaseAdapter {

    public MovieVideoAdapter(Context context, final int layoutResourceId,  final ArrayList<Object> dtos) {
        super(context, layoutResourceId,dtos);

    }

    @Override
    protected void setViewHolderValuesByDTO(Object viewHolder, Object dto) {
        VideoHolder VideoHolder=(VideoHolder)viewHolder;
        MovieVideosDTO video=(MovieVideosDTO)dto;
        VideoHolder.txtVideoName.setText(video.getName());
    }

    @Override
    protected Object  getViewHolderObjectByView(View row) {
        VideoHolder viewHolder =new VideoHolder();
        viewHolder.txtVideoName = (TextView)row.findViewById(R.id.txtVedioName);
        return viewHolder;
    }


    static class VideoHolder
    {
        TextView txtVideoName;
        ImageButton btn;

        // TextView txtContent;
    }
}
