package app.movie.android.com.popularmovie.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import app.movie.android.com.popularmovie.activities.main.MainActivity;

/**
 * Created by Romany on 12/5/2015.
 */
@SuppressWarnings("serial")
public class MoviesDTO implements Serializable {
    public static final String JSON_MOVIES_RESULTS = "results";
    public static final String JSON_MOVIES_PAGE = "page";
    public static final String JSON_MOVIES_TOTAL_PAGES = "total_pages";
    public static final int VISIBLE_THRESHOLD = 8;
    @SerializedName(JSON_MOVIES_PAGE)
    private int page;
    @SerializedName(JSON_MOVIES_RESULTS)
    private ArrayList<MovieDTO> movies;
    @SerializedName(JSON_MOVIES_TOTAL_PAGES)
    private int totalPages;
    private int nextPage;
    private boolean waitingResponse;
    private boolean loadFromDataBase;

    public MoviesDTO() {
        this.movies = new ArrayList<MovieDTO>();
        initDefaultInstanceValues();
    }

    public void initDefaultInstanceValues() {
        this.page = 1;
        this.nextPage = 1;
        this.totalPages = 1;
        this.loadFromDataBase = false;
    }

    public void setLoadFromDataBase(boolean loadFromDataBase) {
        this.loadFromDataBase = loadFromDataBase;
    }

    public int getNextPage() {
        return nextPage;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MovieDTO> movies) {
        this.movies = movies;
    }

    public int getTotalPages() {
        return totalPages;
    }


    public void addNewData(MoviesDTO moviesDTO) {
        this.page = moviesDTO.getPage();
        this.nextPage = moviesDTO.getPage() + 1;
        this.totalPages = moviesDTO.getTotalPages();
        for (MovieDTO movie : moviesDTO.getMovies()) {
            if (MainActivity.movieDBHelper.isFavouriteMovie(movie.getId())) {
                movie.setFavorite(1);

            }
            this.movies.add(movie);
        }

        this.waitingResponse = false;
    }

    public void setWaitingResponse(boolean waitingResponse) {
        this.waitingResponse = waitingResponse;
    }

    public boolean isNeedSendRequest(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        return (!this.loadFromDataBase && !waitingResponse && (totalItemCount - visibleItemCount) < (firstVisibleItem + VISIBLE_THRESHOLD));
    }
}
