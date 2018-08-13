package com.siti.asyst.learnrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.siti.asyst.learnrecyclerview.adapter.AlbumAdapter;
import com.siti.asyst.learnrecyclerview.model.Album;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AlbumAdapter albumAdapter;
    ArrayList<Album> listAlbum = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        //datanya
        for (int i = 0; i <= 10; i++) {
            Album album = new Album();
            album.setTitle("Title " + i);
            album.setArtist("Artist " + i);
            album.setImage("https://images-na.ssl-images-amazon.com/images/I/61McsadO1OL.jpg");
            listAlbum.add(album);
        }

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

    }
}
