package com.siti.asyst.mymovie.adapter;

import android.app.DatePickerDialog;
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
import com.siti.asyst.mymovie.R;
import com.siti.asyst.mymovie.model.Movie;
import com.siti.asyst.mymovie.utility.Constant;
import com.siti.asyst.mymovie.utility.DateUtils;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Movie> mListMovie;
    OnItemClickListener listener;

    public MovieAdapter(Context context, ArrayList<Movie> listMovie, OnItemClickListener listener) {
        this.mContext = context;
        this.mListMovie = listMovie;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemIV = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieAdapter.MyViewHolder(itemIV);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Movie movie = mListMovie.get(position);

        holder.titleTV.setText(movie.getTitle());
        if (!movie.getRelease_date().isEmpty()) {
            holder.releasedateTV.setText(DateUtils.formatDate("yyyy-MM-dd", "dd MMMM yyyy", movie.getRelease_date()));
        } else {
            holder.releasedateTV.setText("Empty Movie");
        }

        holder.overviewTV.setText(movie.getOverview());

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.imagemovie).error(R.drawable.imagemovie);
        Glide.with(mContext).load(Constant.KEY_URL + movie.getPoster_path()).apply(requestOptions).into(holder.movieIV);

        holder.readMoreTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListMovie.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView movieIV;
        TextView titleTV, releasedateTV, overviewTV;
        CardView cardView;
        TextView readMoreTV;

        DatePickerDialog datePickerDialog;

        public MyViewHolder(View itemView) {
            super(itemView);

            movieIV = itemView.findViewById(R.id.image_imageview);
            titleTV = itemView.findViewById(R.id.title_textview);
            releasedateTV = itemView.findViewById(R.id.release_date_textview);
            overviewTV = itemView.findViewById(R.id.overview_textview);
            readMoreTV = itemView.findViewById(R.id.read_more_textview);

            cardView = itemView.findViewById(R.id.cardView);

        }
    }

}
