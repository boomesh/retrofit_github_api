package com.boojoo.sumeshjohn.retrofit_test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;


public class MainActivity extends ActionBarActivity implements Callback <List<Contributor>>{
    private static final String API_URL = "https://api.github.com";

    @Override
    public void success(List<Contributor> contributors, Response response) {
        for (Contributor contributor : contributors) {
            Log.i("TAG", contributor.login + " (" + contributor.contributions + ")");
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e("TAG", error.getLocalizedMessage());
    }


    interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        void contributors(
                @Path("owner") String owner,
                @Path("repo") String repo, Callback<List<Contributor>> cb
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Create a very simple REST adapter which points the GitHub API endpoint.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();

        // Create an instance of our GitHub API interface.
        GitHub github = restAdapter.create(GitHub.class);

        // Fetch and print a list of the contributors to this library.
        github.contributors("square", "retrofit", this);
    }
}
