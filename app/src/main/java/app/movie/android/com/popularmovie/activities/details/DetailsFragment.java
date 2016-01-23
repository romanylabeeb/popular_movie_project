package app.movie.android.com.popularmovie.activities.details;

/**
 * Created by Romany on 1/22/2016.
 */

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.activities.main.MainActivity;
import app.movie.android.com.popularmovie.activities.main.PopularMoviesFragment;
import app.movie.android.com.popularmovie.asynctasks.BaseFetchMovieTask;
import app.movie.android.com.popularmovie.asynctasks.FetchMovieReviewsTask;
import app.movie.android.com.popularmovie.asynctasks.FetchMovieVideosTask;
import app.movie.android.com.popularmovie.customizedadapter.MovieBaseAdapter;
import app.movie.android.com.popularmovie.customizedadapter.MovieReviewAdapter;
import app.movie.android.com.popularmovie.customizedadapter.MovieVideoAdapter;
import app.movie.android.com.popularmovie.customizelist.ExpandableHeightListView;
import app.movie.android.com.popularmovie.model.MovieDTO;
import app.movie.android.com.popularmovie.model.MovieVideosDTO;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {
    private static String LOG_TAG = DetailsFragment.class.getSimpleName();
    private static MovieDTO movieDetail;
    private MovieReviewAdapter movieReviewsAdapter;
    private MovieVideoAdapter movieVideoAdapter;
    private ExpandableHeightListView reviewListView;
    private ExpandableHeightListView videoListView;
    private Button btnFavourite;
    private ImageView poster, backGroundImg;
    private TextView title, overView, releaseDate;
    private RatingBar rating;
    private static ScrollView scrollView;
    private View rootView;
    //public static boolean showDetail;

    public DetailsFragment() {
    }

    public void setMovieDto(MovieDTO movieDto) {
        Log.i(LOG_TAG, "loadMovieDetail");
        this.movieDetail = movieDto;
    }

    public static void hideMovieDetail() {
        //scrollView.setVisibility(View.INVISIBLE);
        movieDetail = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        this.initDetailView(rootView);
        loadMovieDetail();
        return rootView;
    }

    private void initDetailView(View rootView) {
        this.initiateDetailAdapters();
        btnFavourite = (Button) rootView.findViewById(R.id.btnFavouriteMovie);
        reviewListView = this.initiateDetailListView(rootView, movieReviewsAdapter, R.id.list_review_id);
        videoListView = initiateDetailListView(rootView, movieVideoAdapter, R.id.list_video_id);
        poster = (ImageView) rootView.findViewById(R.id.imgMovieDetail);
        backGroundImg = (ImageView) rootView.findViewById(R.id.imgBackMovieDetail);
        title = (TextView) rootView.findViewById(R.id.txtOriginalTitleMovieDetail);
        overView = (TextView) rootView.findViewById(R.id.txtOverViewMovieDetail);
        rating = ((RatingBar) rootView.findViewById(R.id.ratingBarMovieDetail));
        releaseDate = (TextView) rootView.findViewById(R.id.movieReleaseDate);
        scrollView = (ScrollView) rootView.findViewById(R.id.movie_detail_scroll_view_id);
    }

    public void loadMovieDetail() {

        Log.i(LOG_TAG, "loadMovieDetail");
        if (null != movieDetail) {
            scrollView.setVisibility(View.VISIBLE);
            System.out.println("in details");
            this.showMovieDetail();
            this.handleVideoAction(videoListView);
//for favourite button
            this.handleFavoriteButtonAction(btnFavourite);
        }

    }

    private void handleFavoriteButtonAction(final Button btnFavourite) {
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!movieDetail.isFavorite()) {
                    MainActivity.movieDBHelper.makeMovieAsFovourite(movieDetail);
                    movieDetail.setFavorite(1);
                } else {
                    MainActivity.movieDBHelper.deleteFavouriteMovie(movieDetail.getId());
                    movieDetail.setFavorite(0);
                }
                PopularMoviesFragment.notifyChangeMovieStatus(movieDetail.getId(), movieDetail.isFavorite());
                validateFavouriteButton(movieDetail.getId(), btnFavourite);
            }
        });
    }

    private void handleVideoAction(ListView videoListView) {
        // videoListView.setOnClickListener();
        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                MovieVideosDTO video = (MovieVideosDTO) movieVideoAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + video.getKey()));
                startActivity(intent);
            }
        });
    }

    private void showMovieDetail() {

        this.loadExternalMovieDetails(btnFavourite);
        this.validateFavouriteButton(this.movieDetail.getId(), btnFavourite);
        this.loadMovieBasicDetails();
        this.movieDetail.setFavorite(MainActivity.movieDBHelper.isFavouriteMovie(this.movieDetail.getId()) ? 1 : 0);
    }

    private ExpandableHeightListView initiateDetailListView(View rootView, MovieBaseAdapter adapter, int listViewId) {
        ExpandableHeightListView listView = (ExpandableHeightListView) rootView.findViewById(listViewId);
        listView.setAdapter(adapter);
        listView.setExpanded(true);
        return listView;
    }

    private void initiateDetailAdapters() {
        movieReviewsAdapter =
                new MovieReviewAdapter(
                        getActivity(), R.layout.list_reviews, new ArrayList<Object>());
        movieVideoAdapter = new MovieVideoAdapter(getActivity(), R.layout.list_videos, new ArrayList<Object>());

    }

    private void validateFavouriteButton(int movieId, Button btnFavourite) {
        boolean isFavouriteMovie = MainActivity.movieDBHelper.isFavouriteMovie(movieId);
        if (!isFavouriteMovie) {
            btnFavourite.setBackgroundResource(R.drawable.add);
        } else {
            this.makeBtnIsFavourite(btnFavourite);
            btnFavourite.setVisibility(View.VISIBLE);
        }
    }

    private void makeBtnIsFavourite(Button btn) {
        btnFavourite.setBackgroundResource(R.drawable.remove);
    }

    private void loadExternalMovieDetails(Button btnFavourite) {
        if (!this.movieDetail.isFavorite()) {
            BaseFetchMovieTask movieReviewTask = new FetchMovieReviewsTask(Integer.toString(this.movieDetail.getId()), this.movieReviewsAdapter, this.movieDetail, btnFavourite);
            BaseFetchMovieTask movieVideosTask = new FetchMovieVideosTask(Integer.toString(this.movieDetail.getId()), this.movieVideoAdapter, this.movieDetail);
            movieVideosTask.execute();
            movieReviewTask.execute();
            Log.i("done read external data", "");
        } else {
            this.loadExternalDataFromDataBase();
        }
    }

    private void loadExternalDataFromDataBase() {
        this.movieDetail.setMovieReviews(MainActivity.movieDBHelper.getFavoriteMovieReviewsList(this.movieDetail.getId()));
        this.movieDetail.setMovieVideos(MainActivity.movieDBHelper.getFavoriteMovieVideosList(this.movieDetail.getId()));
        this.movieVideoAdapter.clear();
        this.movieVideoAdapter.addAll(this.movieDetail.getMovieVideos());
        this.movieReviewsAdapter.clear();
        this.movieReviewsAdapter.addAll(this.movieDetail.getMovieReviews());
    }

    private void loadMovieBasicDetails() {

        this.loadImage(poster, movieDetail.getPosterImagePath());
        this.loadImage(backGroundImg, movieDetail.getBackImagePath());

        title.setText(movieDetail.getTitle());
        overView.setText(movieDetail.getOverView());
        rating.setRating((float) movieDetail.getVoteRate() / 2.0f);
        releaseDate.setText(movieDetail.getReleaseDate());
    }

    private void loadImage(final ImageView imgView, String imgUrl) {
        Picasso.with(getActivity()).load(imgUrl)
                .fit()
                .into(imgView);

    }
}

