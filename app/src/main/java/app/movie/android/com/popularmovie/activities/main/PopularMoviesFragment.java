package app.movie.android.com.popularmovie.activities.main;

import android.app.Fragment;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.Utilities.Utility;
import app.movie.android.com.popularmovie.activities.details.DetailsFragment;
import app.movie.android.com.popularmovie.asynctasks.BaseFetchMovieTask;
import app.movie.android.com.popularmovie.asynctasks.FetchMoviesTask;
import app.movie.android.com.popularmovie.customizedadapter.MoviesAdapter;
import app.movie.android.com.popularmovie.model.MovieDTO;
import app.movie.android.com.popularmovie.model.MoviesDTO;

/**
 * Created by Romany on 12/5/2015.
 */
public class PopularMoviesFragment extends Fragment {
    public static final String LOG_TAG = PopularMoviesFragment.class.getSimpleName();
    private static MoviesAdapter moviesAdapter;
    private static MoviesDTO moviesDTO;
    public static String SORT_KEY = "";
    private static GridView gridView;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(MovieDTO movieDTO);
    }

    //public static String SORT_KEY;
    public PopularMoviesFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // getLoaderManager().initLoader(FORECAST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    public static void notifyChangeMovieStatus(int movieId, boolean newMovieStatus) {
        for (MovieDTO movie : moviesDTO.getMovies()) {
            if (movie.getId() == movieId) {
                int favoritStatus = (newMovieStatus ? 1 : 0);
                movie.setFavorite(favoritStatus);
                Log.i(LOG_TAG, "here in change movie status");
                break;
            }
        }

        moviesAdapter.setNotifyOnChange(true);
        gridView.setAdapter(moviesAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = this.getGridViewByRootView(rootView);
        this.defineGridViewSelectItemAction(gridView);

        // add scroll action listener
        gridView.setOnScrollListener(new EndlessScrollListener());


        return rootView;
    }

    private GridView getGridViewByRootView(View rootView) {
        moviesAdapter =
                new MoviesAdapter(
                        getActivity(),
                        R.layout.list_movies, new ArrayList<Object>());
        GridView gridView = (GridView) rootView.findViewById(R.id.list_view_movies);

        gridView.setAdapter(moviesAdapter);
        //add selected item action
        return gridView;
    }

    private void defineGridViewSelectItemAction(GridView gridView) {
        Log.i(LOG_TAG, "on defineGridViewSelectItemAction");
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(LOG_TAG, "on defineGridViewSelectItemAction");
                Log.i(LOG_TAG, "on setOnItemClickListener");
                MovieDTO movieDTO = (MovieDTO) moviesAdapter.getItem(position);
                ((Callback) getActivity()).onItemSelected(movieDTO);

            }
        });
    }


    public void getMoviesDtoAndGetSortKeyFromSharedPreferences() {
        Log.i(LOG_TAG, "on getMoviesDtoAndGetSortKeyFromSharedPreferences");
        if (null == this.moviesDTO) {
            this.moviesDTO = new MoviesDTO();
        }
        String newSortKey = Utility.getSortKey(getActivity());
        if (!this.SORT_KEY.equalsIgnoreCase(newSortKey)) {
            DetailsFragment.hideMovieDetail();
            this.SORT_KEY = newSortKey;
            this.moviesDTO.getMovies().clear();
            this.moviesDTO.initDefaultInstanceValues();
            if (!this.SORT_KEY.equalsIgnoreCase("favorites")) {
                loadPopularMovies();
            }
        }
        if (this.SORT_KEY.equalsIgnoreCase("favorites")) {
            this.loadDataFromDataBase();
        }
    }

    private void loadPopularMovies() {
        Log.i(LOG_TAG, "on loadPopularMovies");
        //steps call async class & execute
        moviesDTO.setWaitingResponse(true);
        BaseFetchMovieTask movieTask = new FetchMoviesTask(SORT_KEY, Integer.toString(moviesDTO.getNextPage()), moviesAdapter, moviesDTO);
        movieTask.execute();
    }

    private void loadDataFromDataBase() {
        this.moviesDTO.setLoadFromDataBase(true);
        Log.i(LOG_TAG, "on loadDataFromDataBase");
        ArrayList<MovieDTO> result = MainActivity.movieDBHelper.getAllFavoriteMovies();
        this.moviesDTO.setMovies(result);
        moviesAdapter.clear();
        moviesAdapter.addAll(result);
    }


    public class EndlessScrollListener implements AbsListView.OnScrollListener {
        public EndlessScrollListener() {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (null != moviesDTO && moviesDTO.isNeedSendRequest(firstVisibleItem, visibleItemCount, totalItemCount)) {
                loadPopularMovies();
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // Log.i("onScrollStateChanged scroll","Info");

        }

    }


}