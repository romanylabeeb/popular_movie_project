package app.movie.android.com.popularmovie.asynctasks;

import android.widget.ProgressBar;

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
    //TODO
    //unComment for Test on device or emulator (with no network)

//    private static String jsonResponse1 = "{\n" +
//            "id: 140607,\n" +
//            "results: [\n" +
//            "{\n" +
//            "id: \"53b47955c3a3682ee2009d93\",\n" +
//            "iso_639_1: \"en\",\n" +
//            "key: \"XT4CLuTbI7A\",\n" +
//            "name: \"International Trailer\",\n" +
//            "site: \"YouTube\",\n" +
//            "size: 720,\n" +
//            "type: \"Trailer\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"53db9d59c3a36821cc000d92\",\n" +
//            "iso_639_1: \"en\",\n" +
//            "key: \"2LIQ2-PZBC8\",\n" +
//            "name: \"Trailer #2\",\n" +
//            "site: \"YouTube\",\n" +
//            "size: 720,\n" +
//            "type: \"Trailer\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"53db9d72c3a36821c7000dbe\",\n" +
//            "iso_639_1: \"en\",\n" +
//            "key: \"d96cjJhvlMA\",\n" +
//            "name: \"Trailer #1\",\n" +
//            "site: \"YouTube\",\n" +
//            "size: 720,\n" +
//            "type: \"Trailer\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"53ddf4fec3a3686c56000c87\",\n" +
//            "iso_639_1: \"en\",\n" +
//            "key: \"HeFCpSqfMjU\",\n" +
//            "name: \"\"Gamora & Nebula\" Featurette (2014) Zoe Saldana\",\n" +
//            "site: \"YouTube\",\n" +
//            "size: 720,\n" +
//            "type: \"Featurette\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"53ddf52dc3a3686c49000ce5\",\n" +
//            "iso_639_1: \"en\",\n" +
//            "key: \"Zfw-3JWZWeo\",\n" +
//            "name: \"\"IMAX\" Featurette (2014) James Gunn\",\n" +
//            "site: \"YouTube\",\n" +
//            "size: 720,\n" +
//            "type: \"Featurette\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"53ddf557c3a3686c5a000c9e\",\n" +
//            "iso_639_1: \"en\",\n" +
//            "key: \"vGcmJFVbO4M\",\n" +
//            "name: \"\"Gamora and Drax\" Featurette (2014) Zoe Saldana, Dave Bautista\",\n" +
//            "site: \"YouTube\",\n" +
//            "size: 720,\n" +
//            "type: \"Featurette\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"53ddf573c3a3686c49000cee\",\n" +
//            "iso_639_1: \"en\",\n" +
//            "key: \"AY9v32eVXEo\",\n" +
//            "name: \"\"Peter Quill\" Featurette (2014) Chris Pratt\",\n" +
//            "site: \"YouTube\",\n" +
//            "size: 720,\n" +
//            "type: \"Featurette\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"53ddf598c3a3686c5d000d4b\",\n" +
//            "iso_639_1: \"en\",\n" +
//            "key: \"T1hS3yYMU-g\",\n" +
//            "name: \"\"Definitive Anti Hero\" Featurette (2014) Marvel\",\n" +
//            "site: \"YouTube\",\n" +
//            "size: 720,\n" +
//            "type: \"Featurette\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"53ddf5bdc3a3686c4d000d3e\",\n" +
//            "iso_639_1: \"en\",\n" +
//            "key: \"kKFlUPKiWHc\",\n" +
//            "name: \"\"Blast Off\" Featurette (2014) Chris Pratt, Vin Diesel\",\n" +
//            "site: \"YouTube\",\n" +
//            "size: 720,\n" +
//            "type: \"Featurette\"\n" +
//            "}]}";

    public FetchMovieVideosTask(String movieId, MovieBaseAdapter baseAdapter, Object movieDTO) {
        super(baseAdapter, movieDTO);
        this.url = RequestHandler.getUrlForMovieReviewsBOrVideosByMovieId(movieId, RequestHandler.TRAILER_OPERATION);
    }

    @Override
    protected List<?> getResultListByMapJsonToObjectDto(String jsonResponse) throws JSONException {
        //TODO
        //unComment for Test on device or emulator (with no network)

//        if (jsonResponse.isEmpty()) {
//            jsonResponse = jsonResponse1;
//        }
        MovieDTO movieDTO = (MovieDTO) this.objectDTO;
        String jsonArrayAsString = this.getArrayJsonAsString(jsonResponse);
        Type listType = new TypeToken<List<MovieVideosDTO>>() {
        }.getType();
        ArrayList<MovieVideosDTO> movieVideosList = (ArrayList<MovieVideosDTO>) GSON_MAPPER.fromJson(jsonArrayAsString, listType);
        movieDTO.setMovieVideos(movieVideosList);
        return movieDTO.getMovieVideos();
    }
}
