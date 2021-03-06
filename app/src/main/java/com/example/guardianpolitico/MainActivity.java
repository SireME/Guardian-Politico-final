package com.example.guardianpolitico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    /**
     * Constant value for the news loader ID. We can choose any integer.
     */
    private static final int GUARDIAN_LOADER_ID = 1;


    /**
     * URL for NEWS data from the GIUARDIANS dataset
     */
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/politics";

    /**
     * Adapter for the list of news articles
     */
    private NewsAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mNonewsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        /** TextView that is displayed when the list is empty */
        mNonewsTextView = (TextView) findViewById(R.id.empty_listview);
        newsListView.setEmptyView(mNonewsTextView);

        // Create a new adapter that takes an empty list of articles as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);


        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website for the news article.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current article that was clicked on
                News currentArticle = (News) mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri articleUri = Uri.parse(currentArticle.getUrl());

                // Create a new intent to view the article URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });


        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = LoaderManager.getInstance(this);


        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(GUARDIAN_LOADER_ID, null, this);
    }

    @Override
    // onCreateLoader instantiates and returns a new Loader for the given ID
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {


        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value.`
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("from-date", "2019-01-01");
        uriBuilder.appendQueryParameter("api-key", "cf24b873-4482-4393-8657-c865bfacf91c");
        uriBuilder.appendQueryParameter("show-tags","contributor");

        Log.d("URITEST",uriBuilder.toString());

        // Return the completed uri `https://content.guardianapis.com/politics?order-by=newest&api-key=cf24b873-4482-4393-8657-c865bfacf91c
        return new NewsLoader(this, uriBuilder.toString());


    }

    @Override
    public void onLoadFinished
            (Loader<List<News>> loader, List<News> earthquakes) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        //check for internet connection
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            TextView nointernet = (TextView) findViewById(R.id.No_internet);

            //state that there is no internet connection
            nointernet.setText(R.string.no_internet);
        } else if (networkInfo != null && networkInfo.isConnected()) {

            //There is internet but list is still empty
            // Set empty state text to display "No earthquakes found."
            mNonewsTextView.setText(R.string.no_earthquakes);

        }


        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }


}
