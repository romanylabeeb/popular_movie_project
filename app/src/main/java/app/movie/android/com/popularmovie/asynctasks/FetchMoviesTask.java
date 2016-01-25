package app.movie.android.com.popularmovie.asynctasks;

import org.json.JSONException;

import java.util.List;

import app.movie.android.com.popularmovie.activities.main.PopularMoviesFragment;
import app.movie.android.com.popularmovie.connection.RequestHandler;
import app.movie.android.com.popularmovie.customizedadapter.MovieBaseAdapter;
import app.movie.android.com.popularmovie.model.MoviesDTO;

/**
 * Created by Romany on 1/2/2016.
 */
public class FetchMoviesTask extends BaseFetchMovieTask {

    public FetchMoviesTask(String sortKey, String pageNumber, MovieBaseAdapter baseAdapter, Object moviesDTO) {
        super(baseAdapter, moviesDTO);
        this.url = RequestHandler.getUrlForMoviesListBySortKeyAndPageNumber(sortKey, pageNumber);
    }

    @Override
    protected List<?> getResultListByMapJsonToObjectDto(String jsonResponse) throws JSONException {

        MoviesDTO moviesDTO = (MoviesDTO) this.objectDTO;
        MoviesDTO moviesDTO2 = GSON_MAPPER.fromJson(jsonResponse, MoviesDTO.class);
        moviesDTO.addNewData(moviesDTO2);
        PopularMoviesFragment.showMovieDefaultMovieDetailIfHiddenInTablet(moviesDTO.getMovies().get(0));
        return moviesDTO.getMovies();
    }
}
