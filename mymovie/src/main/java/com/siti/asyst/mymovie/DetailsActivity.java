package com.siti.asyst.mymovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.siti.asyst.mymovie.model.Movie;
import com.siti.asyst.mymovie.utility.Constant;
import com.siti.asyst.mymovie.utility.DateUtils;

public class DetailsActivity extends AppCompatActivity {

    ImageView movieDetailsIV;
    TextView titleDetailsTV, releaseDateDetailsTV, overviewDetailsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieDetailsIV = findViewById(R.id.moviedetails_imageview);
        titleDetailsTV = findViewById(R.id.titledetails_textview);
        releaseDateDetailsTV = findViewById(R.id.releasedatedetails_textview);
        overviewDetailsTV = findViewById(R.id.overviewdetails_textview);

        if (getIntent().getExtras() != null) {
            Movie movie = getIntent().getExtras().getParcelable("movie");

            Glide.with(this).load(Constant.KEY_URL_DETAILS + movie.getPoster_path()).into(movieDetailsIV);
            titleDetailsTV.setText(movie.getTitle());
            releaseDateDetailsTV.setText(DateUtils.formatDate("yyyy-MM-dd", "dd MMMM yyyy", movie.getRelease_date()));
            overviewDetailsTV.setText(movie.getOverview());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
