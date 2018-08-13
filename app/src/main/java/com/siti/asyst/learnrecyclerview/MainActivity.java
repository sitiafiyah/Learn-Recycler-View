package com.siti.asyst.learnrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.siti.asyst.learnrecyclerview.adapter.AlbumAdapter;
import com.siti.asyst.learnrecyclerview.model.Album;
import com.siti.asyst.learnrecyclerview.retrofit.ApiClient;
import com.siti.asyst.learnrecyclerview.retrofit.ApiServices;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AlbumAdapter albumAdapter;
    ArrayList<Album> listAlbum = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(albumAdapter);
        //datanya
//        for (int i = 0; i <= 10; i++) {
//            Album album = new Album();
//            album.setTitle("Title " + i);
//            album.setArtist("Artist " + i);
//            album.setImage("https://images-na.ssl-images-amazon.com/images/I/61McsadO1OL.jpg");
//            listAlbum.add(album);
//        }

        albumAdapter = new AlbumAdapter(this, listAlbum, new AlbumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Album album) {
                Toast.makeText(getApplicationContext(), album.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(albumAdapter);
        //getDataWithVolley();
        getDataWithRetrofit();
    }

    public void getDataWithVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://rallycoding.herokuapp.com/api/music_albums";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //jika sukses
                Log.d("Response", response.toString());

                //memasukan data kedalam list Album
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Album album = new Gson().fromJson(response.getString(i), Album.class);
                        listAlbum.add(album);
                    }
                    albumAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public void getDataWithRetrofit() {
        ApiServices apiServices = ApiClient.newInstance(getApplicationContext()).create(ApiServices.class);
        Call<ArrayList<Album>> call = apiServices.getAlbums();

        call.enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, retrofit2.Response<ArrayList<Album>> response) {
                //buat sukses
                if (response.body() != null) {
                    if (response.body().size() > 0) {

                        listAlbum.addAll(response.body());
                        albumAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                //buat gagal
                Toast.makeText(getApplicationContext(), "Get Data Error", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
