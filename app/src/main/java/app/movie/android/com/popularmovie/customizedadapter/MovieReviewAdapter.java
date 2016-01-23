package app.movie.android.com.popularmovie.customizedadapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.model.MovieReviewDTO;

/**
 * Created by Romany on 12/26/2015.
 */
public class MovieReviewAdapter extends MovieBaseAdapter {

    public MovieReviewAdapter(Context context, final int layoutResourceId, final ArrayList<Object> dtos) {
        super(context, layoutResourceId, dtos);

    }

    @Override
    protected void setViewHolderValuesByDTO(Object viewHolder, Object dto) {
        ReviewHolder reviewHolder = (ReviewHolder) viewHolder;
        MovieReviewDTO review = (MovieReviewDTO) dto;
        reviewHolder.txtAuthor.setText(review.getAuthor());
        reviewHolder.txtContent.setText(review.getContent());
    }

    @Override
    protected Object getViewHolderObjectByView(View row) {
        ReviewHolder viewHolder = new ReviewHolder();
        viewHolder.txtAuthor = (TextView) row.findViewById(R.id.txtAuthor);
        viewHolder.txtContent = (TextView) row.findViewById(R.id.txtContent);
        return viewHolder;
    }


    static class ReviewHolder {
        TextView txtAuthor;
        TextView txtContent;
    }

}
