package app.movie.android.com.popularmovie.activities.main;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    private static String FAVORITE_KEY = "favorites";
    private TextView sortKeyTitle;
    private static GridView gridView;
    public static String SORT_KEY;

    private ProgressBar spinner;

    public void setSortKeyTitle(String sortKeyTitleStr) {
        if (sortKeyTitleStr.equalsIgnoreCase(FAVORITE_KEY)) {
            sortKeyTitleStr = "Favorites Movies";
        } else if (sortKeyTitleStr.equalsIgnoreCase(getString(R.string.pref_most_popular_value))) {
            sortKeyTitleStr = "Most Popular Movies";
        } else if (sortKeyTitleStr.equalsIgnoreCase(getString(R.string.pref_highest_rated_value))) {
            sortKeyTitleStr = "Highest Rated Movies";
        }
        this.sortKeyTitle.setText(sortKeyTitleStr);
    }

    public void resetMoviesList() {
        if (null != moviesDTO)
            moviesDTO.setNextPage(moviesDTO.getPage());
    }

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

    public PopularMoviesFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // getLoaderManager().initLoader(FORECAST_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
    }


    public static void notifyChangeMovieFavoriteStatus(MovieDTO movieDetail, boolean isFavorite) {
        showToastForMovieStatus(movieDetail.getTitle(), movieDetail.isFavorite());
        int i = 0;
        for (MovieDTO movie : moviesDTO.getMovies()) {
            if (movie.getId() == movieDetail.getId()) {
                if (SORT_KEY.equalsIgnoreCase(FAVORITE_KEY)) {
                    removeOrAddMovieToAdapterByNewStatus(movieDetail, i);
                    break;
                } else {
                    Log.i(LOG_TAG, "the old title=" + movie.getTitle());
                    Log.i(LOG_TAG, "the old stat=" + isFavorite);

                    changeMovieStatusInAdapter(movie, isFavorite);

                }
                break;
            }
            i++;
        }
        notifyGridViewAndAdapter();
    }

    private static void showToastForMovieStatus(String movieTitle, boolean newMovieStatus) {
        String toastMessage = movieTitle + " Removed From your favorites movies";
        if (newMovieStatus) {
            toastMessage = movieTitle + " Is Added to your favorites movies";
        }
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(MainActivity.getContext(), toastMessage, duration).show();
    }

    private static void changeMovieStatusInAdapter(MovieDTO movieDetail, boolean isFavorite) {
        int favoriteStatus = (isFavorite ? 1 : 0);

        movieDetail.setFavorite(favoriteStatus);
        notifyGridViewAndAdapter();
    }

    private static void removeOrAddMovieToAdapterByNewStatus(MovieDTO movieDetail, int selectedMovieIndex) {
        int prevIndex = selectedMovieIndex > 0 ? selectedMovieIndex - 1 : 0;
        if (!movieDetail.isFavorite()) {
            moviesAdapter.remove(moviesAdapter.getItem(selectedMovieIndex));

            reloadMovieDetailsFromAdapterByIndex(prevIndex);
        } else {
            moviesAdapter.add(movieDetail);
        }
    }

    public static void notifyGridViewAndAdapter() {
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        spinner = (ProgressBar) rootView.findViewById(R.id.movie_spinner);

        gridView = this.getGridViewByRootView(rootView);

        sortKeyTitle = (TextView) rootView.findViewById(R.id.txtSortKey);
        return rootView;
    }

    private GridView getGridViewByRootView(View rootView) {
        if (null == moviesAdapter) {
            moviesAdapter =
                    new MoviesAdapter(
                            getActivity(),
                            R.layout.list_movies, new ArrayList<Object>());
        }
        gridView = (GridView) rootView.findViewById(R.id.list_view_movies);
        gridView.setAdapter(moviesAdapter);
        //add action listener for selected  item
        this.defineGridViewSelectItemAction(gridView);
        // add scroll action listener
        gridView.setOnScrollListener(new EndlessScrollListener());
        return gridView;
    }


    private void defineGridViewSelectItemAction(GridView gridView) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(LOG_TAG, "on setOnItemClickListener position=" + position);
                moviesAdapter.setSelectedPosition(position);
                notifyGridViewAndAdapter();
                showSelectedMovieDetails((MovieDTO) moviesAdapter.getItem(position));
            }
        });
    }

    public static void showSelectedMovieDetails(MovieDTO selectedMovie) {
        ((Callback) MainActivity.getContext()).onItemSelected(selectedMovie);
    }

    public static void showMovieDefaultMovieDetailIfHiddenInTablet(MovieDTO movieDTO) {
        if (MainActivity.ismTwoPane() && !DetailsFragment.isMovieDetailShown()) {
            showSelectedMovieDetails(movieDTO);
        }
    }

    public void getMoviesDtoAndGetSortKeyFromSharedPreferences() {
        Log.i(LOG_TAG, "on getMoviesDtoAndGetSortKeyFromSharedPreferences");
        boolean loadAtFirstTime = false;
        if (null == this.moviesDTO) {
            Log.i(LOG_TAG, "on moviesDTO==null");
            this.moviesDTO = new MoviesDTO();

        }

        String newSortKey = Utility.getSortKey(getActivity());
        this.setSortKeyTitle(newSortKey);
        Log.i(LOG_TAG, "ssort newSortKey=" + newSortKey);

        if (null == SORT_KEY || !SORT_KEY.equalsIgnoreCase(newSortKey)) {
            Log.i(LOG_TAG, "old sort key=" + SORT_KEY);
            SORT_KEY = newSortKey;
            this.moviesDTO.clearAllData();
            this.moviesDTO.initDefaultInstanceValues();
            if (!SORT_KEY.equalsIgnoreCase(FAVORITE_KEY)) {
                loadPopularMovies();
            }
        }
        if (SORT_KEY.equalsIgnoreCase(FAVORITE_KEY)) {
            this.loadDataFromDataBase();
        }
    }

    private void loadPopularMovies() {
        Log.i(LOG_TAG, "on loadPopularMovies");
        //steps call async class & execute
        moviesDTO.setWaitingResponse(true);
        BaseFetchMovieTask movieTask = new FetchMoviesTask(spinner, SORT_KEY, Integer.toString(moviesDTO.getNextPage()), moviesAdapter, moviesDTO);
        movieTask.execute();
    }

    private void loadDataFromDataBase() {
        this.moviesDTO.setLoadFromDataBase(true);
        Log.i(LOG_TAG, "on loadDataFromDataBase");
        ArrayList<MovieDTO> result = MainActivity.movieDBHelper.getAllFavoriteMovies();
        this.moviesDTO.setMovies(result);
        moviesAdapter.clear();
        moviesAdapter.addAll(result);
        reloadMovieDetailsFromAdapterByIndex(0);

    }

    private static void reloadMovieDetailsFromAdapterByIndex(int index) {
        if (MainActivity.ismTwoPane()) {
            if (moviesAdapter.getCount() >= index) {
                showSelectedMovieDetails((MovieDTO) moviesAdapter.getItem(index));
            } else {
                DetailsFragment.hideMovieDetail();
            }
        }
    }

    public class EndlessScrollListener implements AbsListView.OnScrollListener {
        public EndlessScrollListener() {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            Log.i(LOG_TAG, "onscroll");
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