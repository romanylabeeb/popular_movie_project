package app.movie.android.com.popularmovie.activities.details;

/**
 * Created by Romany on 1/22/2016.
 */

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.movie.android.com.popularmovie.R;
import app.movie.android.com.popularmovie.activities.main.PopularMoviesFragment;
import app.movie.android.com.popularmovie.asynctasks.BaseFetchMovieTask;
import app.movie.android.com.popularmovie.asynctasks.FetchMovieReviewsTask;
import app.movie.android.com.popularmovie.asynctasks.FetchMovieVideosTask;
import app.movie.android.com.popularmovie.customizedadapter.MovieBaseAdapter;
import app.movie.android.com.popularmovie.customizedadapter.MovieReviewAdapter;
import app.movie.android.com.popularmovie.customizedadapter.MovieVideoAdapter;
import app.movie.android.com.popularmovie.customizelist.ExpandableHeightListView;
import app.movie.android.com.popularmovie.data.ContentProviderHelper;
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
    private ScrollView scrollView;
    private View rootView;
    private VideoView videoView;
    public static final String MOVIE_SHARE_HASHTAG = " #MOVIE_APP";

    private static ShareActionProvider mShareActionProvider;

    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static MovieDTO getMovieDetailDTO() {
        return movieDetail;
    }

    public void setMovieDto(MovieDTO movieDto) {
        Log.i(LOG_TAG, "loadMovieDetail");
        this.movieDetail = movieDto;
    }

    public static void hideMovieDetail() {
        movieDetail = null;
    }

    public static boolean isMovieDetailShown() {
        return null != movieDetail;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);
        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.share).getActionProvider();

    }

    public static void defineShareAction(String trailerKey) {
        if (null != mShareActionProvider) {
            mShareActionProvider.setShareIntent(createShareTrailerIntent(trailerKey));
        }
    }

    private static Intent createShareTrailerIntent(String trailerKey) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + trailerKey + MOVIE_SHARE_HASHTAG);
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, MOVIE_SHARE_HASHTAG);
        return shareIntent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        this.initDetailView(rootView);
        loadMovieDetail();
        return rootView;
    }

    private void initDetailView(View rootView) {
        this.initiateDetailAdapters();
        videoView = (VideoView) rootView.findViewById(R.id.video_view);
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
            this.showMovieDetail();
            this.handleVideoAction(videoListView);
//for favourite button
            this.btnFavoriteAction(btnFavourite);
        }

    }

    private void btnFavoriteAction(final Button btnFavourite) {
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!movieDetail.isFavorite()) {
                    movieDetail.setFavorite(1);
                    ContentProviderHelper.insertNewFavoriteMovie(getActivity().getContentResolver(), movieDetail);
                    //MainActivity.movieDBHelper.makeMovieAsFovourite(movieDetail);
                    movieDetail.setFavorite(1);
                } else {
                    ContentProviderHelper.deleteFavoriteMovie(getActivity().getContentResolver(), movieDetail.getId(),
                            movieDetail.getMovieReviews().size(), movieDetail.getMovieVideos().size());
                    movieDetail.setFavorite(0);
                }
                PopularMoviesFragment.notifyChangeMovieFavoriteStatus(movieDetail, movieDetail.isFavorite());
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
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + video.getKey()));
                startActivity(intent);
            }
        });
    }

    private void showMovieDetail() {

        this.loadExternalMovieDetails(btnFavourite);
        this.validateFavouriteButton(this.movieDetail.getId(), btnFavourite);
        this.loadMovieBasicDetails();
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

        if (!movieDetail.isFavorite()) {
            btnFavourite.setBackgroundResource(R.drawable.add);
        } else {
            btnFavourite.setBackgroundResource(R.drawable.remove);
            btnFavourite.setVisibility(View.VISIBLE);
        }
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


        if (null == this.movieDetail.getMovieReviews() || this.movieDetail.getMovieReviews().isEmpty()) {
            this.loadTrailerAndReviewsUsingContentProvider();
        }
        this.movieVideoAdapter.clear();
        this.movieVideoAdapter.addAll(this.movieDetail.getMovieVideos());
        this.movieReviewsAdapter.clear();
        this.movieReviewsAdapter.addAll(this.movieDetail.getMovieReviews());

    }

    private void loadTrailerAndReviewsUsingContentProvider() {
        this.movieDetail.setMovieReviews(ContentProviderHelper.getMovieReviewsById(getActivity().getContentResolver(), movieDetail.getId()));
        this.movieDetail.setMovieVideos(ContentProviderHelper.getMovieTrailerById(getActivity().getContentResolver(), movieDetail.getId()));
    }

    private void loadMovieBasicDetails() {
        this.loadImage(poster, movieDetail.getPosterImagePath());
        this.loadImage(backGroundImg, movieDetail.getBackImagePath());
        title.setText(movieDetail.getTitle());
        overView.setText(movieDetail.getOverView());
        rating.setRating((float) movieDetail.getVoteRate() * 1.0f);
        releaseDate.setText(movieDetail.getReleaseDate());
    }

    private void loadImage(final ImageView imgView, String imgUrl) {
        Picasso.with(getActivity()).load(imgUrl)
                .fit()
                .placeholder(R.drawable.movie_placeholder)
                .error(R.drawable.movie_placeholder_error)
                .into(imgView);
    }
}

