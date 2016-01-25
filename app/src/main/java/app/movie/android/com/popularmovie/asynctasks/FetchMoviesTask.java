package app.movie.android.com.popularmovie.asynctasks;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.util.List;

import app.movie.android.com.popularmovie.activities.details.DetailsFragment;
import app.movie.android.com.popularmovie.activities.main.MainActivity;
import app.movie.android.com.popularmovie.activities.main.PopularMoviesFragment;
import app.movie.android.com.popularmovie.connection.RequestHandler;
import app.movie.android.com.popularmovie.customizedadapter.MovieBaseAdapter;
import app.movie.android.com.popularmovie.model.MoviesDTO;

/**
 * Created by Romany on 1/2/2016.
 */
public class FetchMoviesTask extends BaseFetchMovieTask {
    //TODO
    //unComment for Test on device or emulator (with no network)

    //    private static String jsonResponse1 = "{\"page\":1,\n" +
//            "\"results\":[{\"poster_path\":\"\\/fYzpM9GmpBlIC893fNjoWCwE24H.jpg\",\n" +
//            "\"adult\":false,\n" +
//            "\"overview\":\"Thirty years after defeating the Galactic Empire, Han Solo and his allies face anew threat from the evil Kylo Ren and his army of Stormtroopers.\",\n" +
//            " \"release_date\":\"2015-12-18\",\n" +
//            "\"genre_ids\":[28,\n" +
//            "12,\n" +
//            "878,\n" +
//            "14],\n" +
//            "\"id\":140607,\n" +
//            "\"original_title\":\"Star Wars: The Force Awakens\",\n" +
//            "\"original_language\":\"en\",\n" +
//            "\"title\":\"Star Wars: The Force Awakens\",\n" +
//            "\"backdrop_path\":\"\\/njv65RTipNSTozFLuF85jL0bcQe.jpg\",\n" +
//            "\"popularity\":47.133397,\n" +
//            "\"vote_count\":2453,\n" +
//            "\"video\":\"false\",\n" +
//            "\"vote_average\":7.84},\n" +
//            "{\"poster_path\":\"\\/mSvpKOWbyFtLro9BjfEGqUw5dXE.jpg\",\n" +
//            "\"adult\":\"false\",\n" +
//            "\"overview\":\"A cryptic message from Bond’s past sends him on a trail to uncover a sinisterorganization. While M battles political forces to keep the secret service alive, Bond peels back the layers of deceit to reveal the terrible truth behind SPECTRE.\",\n" +
//            " \"release_date\":\"2015-10-26\",\n" +
//            "\"genre_ids\":[28,\n" +
//            "12,\n" +
//            "80],\n" +
//            "\"id\":206647,\n" +
//            "\"original_title\":\"Spectre\",\n" +
//            "\"original_language\":\"en\",\n" +
//            "\"title\":\"Spectre\",\n" +
//            "\"backdrop_path\":\"\\/wVTYlkKPKrljJfugXN7UlLNjtuJ.jpg\",\n" +
//            "\"popularity\":38.415699,\n" +
//            "\"vote_count\":1635,\n" +
//            "\"video\":false,\n" +
//            "\"vote_average\":6.33},\n" +
//            "{\"poster_path\":\"\\/oXUWEc5i3wYyFnL1Ycu8ppxxPvs.jpg\",\n" +
//            "\"adult\":false,\n" +
//            "\"overview\":\"In the 1820s, a frontiersman, Hugh Glass, sets out on a path of vengeance against those who left him for dead after a bearmauling.\",\n" +
//            " \"release_date\":\"2015-12-25\",\n" +
//            "\"genre_ids\":[37,18,12,53],\n" +
//            "\"id\":281957,\n" +
//            "\"original_title\":\"The Revenant\",\n" +
//            "\"original_language\":\"en\",\n" +
//            "\"title\":\"The Revenant\",\n" +
//            "\"backdrop_path\":\"\\/uS1SkjVviraGfFNgkDwe7ohTm8B.jpg\",\n" +
//            "\"popularity\":35.872103,\n" +
//            "\"vote_count\":629,\n" +
//            "\"video\":false,\n" +
//            "\"vote_average\":7.27},\n" +
//            "{\"poster_path\":\"\\/5aGhaIHYuQbqlHWvWYqMCnj40y2.jpg\",\n" +
//            "\"adult\":\"false\",\n" +
//            "\"overview\":\"During a manned mission to Mars, Astronaut Mark Watney is presumed dead after a fierce storm and left behind by his crew. But Watney has survived and finds himselfstranded and alone on the hostile planet. With only meager supplies, he must draw upon his ingenuit, wit and spirit to subsist and find a way to signal to Earth that he is alive.\",\n" +
//            " \"release_date\":\"2015-10-02\",\n" +
//            "\"genre_ids\":[18,\n" +
//            "12,\n" +
//            "878],\n" +
//            "\"id\":286217,\n" +
//            "\"original_title\":\"The Martian\",\n" +
//            "\"original_language\":\"en\",\n" +
//            "\"title\":\"The Martian\",\n" +
//            "\"backdrop_path\":\"\\/sy3e2e4JwdAtd2oZGA2uUilZe8j.jpg\",\n" +
//            "\"popularity\":27.211469,\n" +
//            "\"vote_count\":2074,\n" +
//            "\"video\":false,\n" +
//            "\"vote_average\":7.63}\n" +
//            "\n" +
//            "]}\n";
    protected ProgressBar spinner;

    public FetchMoviesTask(ProgressBar spinner, String sortKey, String pageNumber, MovieBaseAdapter baseAdapter, Object moviesDTO) {
        super(baseAdapter, moviesDTO);
        this.spinner = spinner;
        this.spinner.setVisibility(View.VISIBLE);
        this.url = RequestHandler.getUrlForMoviesListBySortKeyAndPageNumber(sortKey, pageNumber);
    }

    @Override
    protected List<?> getResultListByMapJsonToObjectDto(String jsonResponse) throws JSONException {
        //TODO
        //unComment for Test on device or emulator (with no network)

//        if (jsonResponse.isEmpty()) {
//            jsonResponse = jsonResponse1;
//        }
        MoviesDTO moviesDTO = (MoviesDTO) this.objectDTO;
        MoviesDTO moviesDTO2 = GSON_MAPPER.fromJson(jsonResponse, MoviesDTO.class);
        moviesDTO.addNewData(moviesDTO2);
        PopularMoviesFragment.showMovieDefaultMovieDetailIfHiddenInTablet(moviesDTO.getMovies().get(0));

        return moviesDTO.getMovies();
    }


    @Override
    protected void onPostExecute(List<?> results) {
        spinner.setVisibility(View.GONE);
        super.onPostExecute(results);
    }
}
