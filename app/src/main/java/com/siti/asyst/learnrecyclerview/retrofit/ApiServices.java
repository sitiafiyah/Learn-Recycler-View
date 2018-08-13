package com.siti.asyst.learnrecyclerview.retrofit;

import com.siti.asyst.learnrecyclerview.model.Album;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {

    @GET("music_albums")
    Call<ArrayList<Album>> getAlbums();

}
