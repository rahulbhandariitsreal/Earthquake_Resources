package com.example.equakeacitvity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.equakeacitvity.adapter.EarthquakeAdapter;
import com.example.equakeacitvity.model.Earthquake;
import com.example.equakeacitvity.model.QueryUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {




    private TextView textView;
    ProgressBar progressBar;
    private  LoaderManager loaderManager;
    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //find the current earthquake clicked
            Earthquake earthquake = adapter.getItem(position);

            Uri uri = Uri.parse(earthquake.getUrl());

            //create a new intent to view the uri
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(websiteIntent);
        }
    };

    private EarthquakeAdapter adapter;
    private ArrayList<Earthquake> earthquakes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar=findViewById(R.id.loading_indicator);
        // Create a fake list of earthquake locations.
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        textView=findViewById(R.id.emptystatetextview);
        adapter = new EarthquakeAdapter(MainActivity.this, new ArrayList<Earthquake>(), 0);
//        adapter=new EarthquakeAdapter(MainActivity.this,new ArrayList<Earthquake>(),0);
//        earthquakeListView.setAdapter(adapter);
//        ExecutorService executorService= Executors.newSingleThreadExecutor();
//        Handler handler=new Handler(Looper.getMainLooper());
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                   earthquakes = QueryUtils.extractEarthquakes();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Create a new {@link ArrayAdapter} of earthquakes
//adapter.clear();
//adapter.addAll(earthquakes);
//                        // Set the adapter on the {@link ListView}
//                        // so the list can be populated in the user interface
//
//                    }
//                });
//            }
//        });


        earthquakeListView.setAdapter(adapter);
//MyAsyncTask myAsyncTask=new MyAsyncTask();
//myAsyncTask.execute();


        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        //if network avaliabe
        if(networkInfo  != null && networkInfo.isConnected()){

            loaderManager=getSupportLoaderManager();
            loaderManager.initLoader(1,null,this);

        }else{
            progressBar.setVisibility(View.GONE);
            textView.setText(R.string.internet);
        }

   earthquakeListView.setEmptyView(textView);

        earthquakeListView.setOnItemClickListener(clickListener);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoaderEarthquake(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {

        progressBar.setVisibility(View.GONE);

        textView.setText(R.string.nodata);

        adapter.clear();
        if(data != null && !data.isEmpty()) {
         adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Earthquake>> loader) {
        adapter.clear();
        adapter.addAll(new ArrayList<Earthquake>());
    }
}

//using asyntaskloader
//    private class MyAsyncTask extends AsyncTask<Void,Void ,ArrayList<Earthquake>>{
//
//        @Override
//        protected ArrayList<Earthquake> doInBackground(Void... voids) {
//            ArrayList<Earthquake> result= null;
//            try {
//                result = QueryUtils.extractEarthquakes();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            return result;
//        }
//
//
//
//        @Override
//        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
//            adapter.clear();;
//            adapter.addAll(earthquakes);
//        }
//    }

//AsyncTaskLoader
 class AsyncTaskLoaderEarthquake extends AsyncTaskLoader<ArrayList<Earthquake>> {

    public AsyncTaskLoaderEarthquake(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Earthquake> loadInBackground() {
        ArrayList<Earthquake> earthquake_list = new ArrayList<>();
        try {
            earthquake_list = QueryUtils.extractEarthquakes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return earthquake_list;
    }
}
