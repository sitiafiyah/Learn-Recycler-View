package com.siti.asyst.learnrecyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.siti.asyst.learnrecyclerview.R;
import com.siti.asyst.learnrecyclerview.model.Album;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Album> mListAlbum;
    OnItemClickListener listener;

    public AlbumAdapter(Context context, ArrayList<Album> listAlbum, OnItemClickListener listener) { //konstruktor
        this.mContext = context;
        this.mListAlbum = listAlbum;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemVI = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);

        return new AlbumAdapter.MyViewHolder(itemVI);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Album album = mListAlbum.get(position);

        holder.artistTV.setText(album.getArtist());
        holder.albumTV.setText(album.getTitle());

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.image1).error(R.drawable.image1);
        Glide.with(mContext).load(album.getImage()).apply(requestOptions).into(holder.albumIV);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(album);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListAlbum.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Album album);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView albumIV;
        TextView albumTV, artistTV;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            albumIV = itemView.findViewById(R.id.album_imageview);
            albumTV = itemView.findViewById(R.id.album_textview);
            artistTV = itemView.findViewById(R.id.artistname_textview);

            cardView = itemView.findViewById(R.id.cardview);

        }

    }

}
