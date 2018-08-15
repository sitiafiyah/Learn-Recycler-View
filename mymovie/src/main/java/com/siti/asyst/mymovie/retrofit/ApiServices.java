package com.siti.asyst.mymovie.retrofit;

import com.siti.asyst.mymovie.retrofit.response.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("discover/movie")
        //Call<MovieResponse> getMovies(@Query("api_key") String api_key, @Query("page") int page);
    Call<MovieResponse> getMovies(@Query("api_key") String api_key, @Query("year") String year, @Query("page") int page, @Query("sort_by") String sort_by);


    @GET("search/movie")
    Call<MovieResponse> searchMovie(@Query("api_key") String api_key, @Query("query") String query, @Query("page") int page);
}
