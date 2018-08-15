package com.siti.asyst.mymovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.siti.asyst.mymovie.adapter.MovieAdapter;
import com.siti.asyst.mymovie.fragment.FilterBottomSheet;
import com.siti.asyst.mymovie.fragment.SearchBottomSheet;
import com.siti.asyst.mymovie.model.Movie;
import com.siti.asyst.mymovie.retrofit.ApiClient;
import com.siti.asyst.mymovie.retrofit.ApiServices;
import com.siti.asyst.mymovie.retrofit.response.MovieResponse;
import com.siti.asyst.mymovie.utility.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FilterBottomSheet.OnSubmitButtonListener, SearchBottomSheet.OnSubmitButtonListener {

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    ArrayList<Movie> listMovie = new ArrayList<>();
    String year = "", sort_by = "popularity.desc";
    String query = "";

    ProgressBar progressBar;
    int halaman = 1;
    boolean isLoading = false;
    int total_pages;
//    String filterYear="";
//    String filterSortBy="popularity.desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(movieAdapter);

        movieAdapter = new MovieAdapter(this, listMovie, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                //Toast.makeText(getApplicationContext(), movie.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(movieAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        if (total_pages >= halaman) {
                            progressBar.setVisibility(View.VISIBLE);
                            isLoading = true;
                            getDataWithRetrofit();
                            if (query.equalsIgnoreCase("")) {
                                getDataWithRetrofit();
                            } else {
                                getDataSearch();
                            }
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        getDataWithRetrofit();
    }

    public void getDataWithRetrofit() {
        ApiServices apiServices = ApiClient.newInstance(getApplicationContext()).create(ApiServices.class);
        Call<MovieResponse> call = apiServices.getMovies(Constant.KEY_API_KEY, year, halaman, sort_by);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    if (response.body().getResults().size() > 0) {
                        //Log.d("size", "size"+response.body().getResults().size());
                        total_pages = response.body().getTotal_pages();
                        halaman = response.body().getPage() + 1;
                        listMovie.addAll(response.body().getResults());
                        movieAdapter.notifyDataSetChanged();
                        isLoading = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void getDataSearch() {
        ApiServices apiServices = ApiClient.newInstance(getApplicationContext()).create(ApiServices.class);
        Call<MovieResponse> call = apiServices.searchMovie(Constant.KEY_API_KEY, query, halaman);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    if (response.body().getResults().size() > 0) {
                        //Log.d("size", "size"+response.body().getResults().size());
                        total_pages = response.body().getTotal_pages();
                        halaman = response.body().getPage() + 1;
                        listMovie.addAll(response.body().getResults());
                        movieAdapter.notifyDataSetChanged();
                        isLoading = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onSubmitButton(String year, String sortBy) {
        this.year = year;
        this.sort_by = sortBy;

        listMovie.clear();
        movieAdapter.notifyDataSetChanged();

        halaman = 1;

        getDataWithRetrofit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.filter_main_menu:

                FilterBottomSheet filterBottomSheet = FilterBottomSheet.newInstance(year, sort_by);
                filterBottomSheet.show(getSupportFragmentManager(), "Filter Bottom Sheet");
                break;

            case R.id.search_main_menu:

                SearchBottomSheet searchBottomSheet = SearchBottomSheet.newInstance(query);
                searchBottomSheet.show(getSupportFragmentManager(), "Search Bottom Sheet");

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSubmitButton(String search) {
        this.query = search;

        listMovie.clear();
        movieAdapter.notifyDataSetChanged();

        halaman = 1;

        getDataSearch();
    }
}
