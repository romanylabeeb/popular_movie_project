package app.movie.android.com.popularmovie.asynctasks;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

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
    //TODO
    //unComment for Test on device or emulator (with no network)
//
//    String jsonResponse1 = "{\n" +
//            "id: 140607,\n" +
//            "page: 1,\n" +
//            "results: [\n" +
//            "{\n" +
//            "id: \"53f11b7c0e0a2675b8004053\",\n" +
//            "author: \"Binawoo\",\n" +
//            "content: \"This movie was so AWESOME! I loved it all and i had a bad day before watching it but it turned it around. I love action packed movies and this was great.\",\n" +
//            "url: \"http://j.mp/YlRX45\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"53f11c0b0e0a2675ac003e7d\",\n" +
//            "author: \"Andres Gomez\",\n" +
//            "content: \"Really funny and with great spcial effects. Probably, one of the movies not to miss this year.\",\n" +
//            "url: \"http://j.mp/1rK5avZ\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"53fafb790e0a267a7b006e20\",\n" +
//            "author: \"Eazyryda\",\n" +
//            "content: \"I ΛM GЯӨӨƬ!\",\n" +
//            "url: \"http://j.mp/VKl6Eo\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"5459072dc3a36839ac001d55\",\n" +
//            "author: \"Grant English\",\n" +
//            "content: \"This was a pleasant surprise of a movie, full of great humor as well as an intriguing story. It's quick and zany at times. Think <em>Psych</em> in space (back when Psych was funny). The pace is quick and the story line is easy to pick up and follow. What makes the film work is something that early Lucas and Joss Whedon understand: great sci-fi movies are really westerns at heart. There is a relationship with nature, a broken, compromised hero, a deep necessity for teamwork, and the story happens OUT there in the wild. <em>Guardians of the Galaxy</em> has ALL of this. <em>Guardians</em> is already set up for a sequel so we will see if Marvel can continue the magic. It's a great addition to the Marvel universe.\",\n" +
//            "url: \"http://j.mp/1yV7B38\"\n" +
//            "},\n" +
//            "{\n" +
//            "id: \"5488c29bc3a3686f4a00004a\",\n" +
//            "author: \"Travis Bell\",\n" +
//            "content: \"Like most of the reviews here, I agree that Guardians of the Galaxy was an absolute hoot. Guardians never takes itself too seriously which makes this movie a whole lot of fun. The cast was perfectly chosen and even though two of the main five were CG, knowing who voiced and acted alongside them completely filled out these characters. Guardians of the Galaxy is one of those rare complete audience pleasers. Good fun for everyone!\",\n" +
//            "url: \"http://j.mp/1wi56Ym\"\n" +
//            "}\n" +
//            "],\n" +
//            "total_pages: 1,\n" +
//            "total_results: 5\n" +
//            "}";

    public FetchMovieReviewsTask(String movieId, MovieBaseAdapter baseAdapter, Object movieDTO, Button favoriteBTN) {

        super(baseAdapter, movieDTO);
        this.url = RequestHandler.getUrlForMovieReviewsBOrVideosByMovieId(movieId, RequestHandler.REVIEW_OPERATION);
        this.favoriteBTN = favoriteBTN;
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
