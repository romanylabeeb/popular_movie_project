package app.movie.android.com.popularmovie.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import app.movie.android.com.popularmovie.R;

/**
 * Created by Romany on 1/22/2016.
 */
public class Utility {

    public static String getSortKey(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String newSortKey = prefs.getString(context.getString(R.string.pref_sort_list_key), context.getString(R.string.pref_default_sort_value));
        return newSortKey;
    }
}
