package app.movie.android.com.popularmovie.customizedadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Romany on 12/26/2015.
 */
public abstract class MovieBaseAdapter extends ArrayAdapter<Object> {
    protected final Context context;
    protected ArrayList<Object> dtos;
    protected int layoutResourceId;


    public MovieBaseAdapter(Context context, final int layoutResourceId, final ArrayList<Object> dtos) {
        super(context, layoutResourceId, dtos);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.dtos = dtos;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object dto = dtos.get(position);
        Object viewHolder = null; // view lookup cache stored in tag
        View row = convertView;

        if (row == null) {
            row = LayoutInflater.from(getContext()).inflate(this.layoutResourceId, parent, false);
            viewHolder = this.getViewHolderObjectByView(row);
            row.setTag(viewHolder);
        } else {
            viewHolder = (Object) row.getTag();
        }
        this.setViewHolderValuesByDTO(viewHolder, dto);
        // row.setOnClickListener(context);
        return row;
    }

    protected abstract void setViewHolderValuesByDTO(Object viewHolder, Object dto);

    protected abstract Object getViewHolderObjectByView(View row);

}
