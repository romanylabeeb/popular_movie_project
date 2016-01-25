package app.movie.android.com.popularmovie.asynctasks;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.movie.android.com.popularmovie.connection.RequestHandler;
import app.movie.android.com.popularmovie.customizedadapter.MovieBaseAdapter;
import app.movie.android.com.popularmovie.model.MovieDTO;
import app.movie.android.com.popularmovie.model.MovieVideosDTO;

/**
 * Created by Romany on 1/2/2016.
 */
public class FetchMovieVideosTask extends BaseFetchMovieTask {

    public FetchMovieVideosTask(String movieId, MovieBaseAdapter baseAdapter, Object movieDTO) {
        super(baseAdapter, movieDTO);
        this.url = RequestHandler.getUrlForMovieReviewsBOrVideosByMovieId(movieId, RequestHandler.TRAILER_OPERATION);
    }

    @Override
    protected List<?> getResultListByMapJsonToObjectDto(String jsonResponse) throws JSONException {

        MovieDTO movieDTO = (MovieDTO) this.objectDTO;
        String jsonArrayAsString = this.getArrayJsonAsString(jsonResponse);
        Type listType = new TypeToken<List<MovieVideosDTO>>() {
        }.getType();
        ArrayList<MovieVideosDTO> movieVideosList = (ArrayList<MovieVideosDTO>) GSON_MAPPER.fromJson(jsonArrayAsString, listType);
        movieDTO.setMovieVideos(movieVideosList);
        return movieDTO.getMovieVideos();
    }
}
