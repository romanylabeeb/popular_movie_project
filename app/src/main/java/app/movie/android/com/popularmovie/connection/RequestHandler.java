package app.movie.android.com.popularmovie.connection;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import app.movie.android.com.popularmovie.BuildConfig;


/**
 * Created by Profit3 on 21/11/2015.
 */
public class RequestHandler {
    private final String LOG_TAG = RequestHandler.class.getSimpleName();
    // These two need to be declared outside the try/catch
    // so that they can be closed in the finally block.

    private static String APPID = BuildConfig.POULAR_MOVIE_API_KEY;
    public static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
    public static final String MOVIE_OPERATION_BASE_URL = "https://api.themoviedb.org/3/movie";

    public static final String REVIEW_OPERATION = "reviews";
    public static final String TRAILER_OPERATION = "videos";
    public static final String SORTED_BY_PARAM = "sort_by";
    public static final String API_KEY_PARAM = "api_key";
    public static final String PAGE_PARAM = "page";


    public static String getUrlForMoviesListBySortKeyAndPageNumber(String sortKey, String pageNumber) {
        Uri buildURL = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(PAGE_PARAM, pageNumber).appendQueryParameter(SORTED_BY_PARAM, sortKey).appendQueryParameter(API_KEY_PARAM, APPID).build();
        return buildURL.toString();
    }

    public static String getUrlForMovieReviewsBOrVideosByMovieId(String movieId, String key) {
        Uri buildURL = Uri.parse(MOVIE_OPERATION_BASE_URL).buildUpon().appendPath(movieId).appendPath(key).appendQueryParameter(API_KEY_PARAM, APPID).build();
        System.out.println("current " + key + " url=" + buildURL.toString());
        return buildURL.toString();
    }

    public String getResponseByUrl(String baseUrl) {
        HttpsURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String moviesJsonStr = "";
        try {
            URL url = new URL(baseUrl);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
// Send post request
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = (urlConnection.getInputStream());
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return moviesJsonStr;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return moviesJsonStr;
    }


}
