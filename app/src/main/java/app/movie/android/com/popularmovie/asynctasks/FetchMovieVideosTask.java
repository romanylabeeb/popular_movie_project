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
//        jsonResponse = "{\n" +
//                "id: 206647,\n" +
//                "results: [\n" +
//                "{\n" +
//                "id: \"5641eb2fc3a3685bdc002e0b\",\n" +
//                "iso_639_1: \"en\",\n" +
//                "key: \"BOVriTeIypQ\",\n" +
//                "name: \"Spectre Ultimate 007 Teaser\",\n" +
//                "site: \"YouTube\",\n" +
//                "size: 1080,\n" +
//                "type: \"Teaser\"\n" +
//                "},\n" +
//                "{\n" +
//                "id: \"56437778c3a36870e30027df\",\n" +
//                "iso_639_1: \"en\",\n" +
//                "key: \"7GqClqvlObY\",\n" +
//                "name: \"Trailer #2\",\n" +
//                "site: \"YouTube\",\n" +
//                "size: 1080,\n" +
//                "type: \"Trailer\"\n" +
//                "},\n" +
//                "{\n" +
//                "id: \"56437756c3a36870ec0023f7\",\n" +
//                "iso_639_1: \"en\",\n" +
//                "key: \"xf8wVezS3JY\",\n" +
//                "name: \"#1 Movie in the world!\",\n" +
//                "site: \"YouTube\",\n" +
//                "size: 1080,\n" +
//                "type: \"Clip\"\n" +
//                "}\n" +
//                "]\n" +
//                "}";
        MovieDTO movieDTO = (MovieDTO) this.objectDTO;
        String jsonArrayAsString = this.getArrayJsonAsString(jsonResponse);
        Type listType = new TypeToken<List<MovieVideosDTO>>() {
        }.getType();
        ArrayList<MovieVideosDTO> movieVideosList = (ArrayList<MovieVideosDTO>) GSON_MAPPER.fromJson(jsonArrayAsString, listType);
        movieDTO.setMovieVideos(movieVideosList);
        return movieDTO.getMovieVideos();
    }
}
