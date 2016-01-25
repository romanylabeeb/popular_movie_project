package app.movie.android.com.popularmovie.asynctasks;

import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.movie.android.com.popularmovie.connection.RequestHandler;
import app.movie.android.com.popularmovie.customizedadapter.MovieBaseAdapter;
import app.movie.android.com.popularmovie.model.MovieDTO;
import app.movie.android.com.popularmovie.model.MovieReviewDTO;

/*
 * Created by Romany on 1/2/2016.
 */
public class FetchMovieReviewsTask extends BaseFetchMovieTask {
    private Button favoriteBTN;

    public FetchMovieReviewsTask(String movieId, MovieBaseAdapter baseAdapter, Object movieDTO, Button favoriteBTN) {
        super(baseAdapter, movieDTO);
        this.url = RequestHandler.getUrlForMovieReviewsBOrVideosByMovieId(movieId, RequestHandler.REVIEW_OPERATION);
        this.favoriteBTN = favoriteBTN;
    }

    @Override
    protected List<?> getResultListByMapJsonToObjectDto(String jsonResponse) throws JSONException {

        MovieDTO movieDTO = (MovieDTO) this.objectDTO;
        String jsonArrayAsString = this.getArrayJsonAsString(jsonResponse);
        Type listType = new TypeToken<List<MovieReviewDTO>>() {
        }.getType();
        ArrayList<MovieReviewDTO> movieReviewsList = (ArrayList<MovieReviewDTO>) GSON_MAPPER.fromJson(jsonArrayAsString, listType);
        movieDTO.setMovieReviews(movieReviewsList);
        return movieDTO.getMovieReviews();
    }


    @Override
    protected void onPostExecute(List<?> results) {
        super.onPostExecute(results);
        favoriteBTN.setVisibility(View.VISIBLE);

    }
}
