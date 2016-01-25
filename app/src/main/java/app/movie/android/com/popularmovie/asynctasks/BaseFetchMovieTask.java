package app.movie.android.com.popularmovie.asynctasks;


import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.movie.android.com.popularmovie.connection.RequestHandler;
import app.movie.android.com.popularmovie.customizedadapter.MovieBaseAdapter;

/**
 * Created by Romany on 1/2/2016.
 */
public abstract class BaseFetchMovieTask extends AsyncTask<String, Void, List<?>> {
    private final String LOG_TAG = BaseFetchMovieTask.class.getSimpleName();
    public static final String JSON_ARRAY_RESULTS = "results";
    public final static RequestHandler appConnection = new RequestHandler();
    public final static Gson GSON_MAPPER = new Gson();
    MovieBaseAdapter baseAdapter;
    protected Object objectDTO;
    protected String url;

    public BaseFetchMovieTask(MovieBaseAdapter baseAdapter, Object objectDTO) {
        this.baseAdapter = baseAdapter;
        this.objectDTO = objectDTO;
    }

    @Override
    protected List<?> doInBackground(String... params) {
        String jsonResponse = this.appConnection.getResponseByUrl(this.url);

        try {
            if (null != jsonResponse) {
                System.out.println("url=:" + this.url);
                return this.getResultListByMapJsonToObjectDto(jsonResponse);
            } else {
                Log.e(LOG_TAG, "Error no connection");
                //TODO
                //unComment for Test on device or emulator (with no network)
                //return this.getResultListByMapJsonToObjectDto("");
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * add result to main activity
     *
     * @param results
     */
    @Override
    protected void onPostExecute(List<?> results) {
        if (results != null) {
            this.baseAdapter.clear();
            this.baseAdapter.addAll(results);

        }


    }

    protected abstract List<?> getResultListByMapJsonToObjectDto(String jsonResponse) throws JSONException;

    protected String getArrayJsonAsString(String jsonResponse) throws JSONException {
        JSONObject responseJsonObject = new JSONObject(jsonResponse);
        return responseJsonObject.getJSONArray(JSON_ARRAY_RESULTS).toString();
    }


}
