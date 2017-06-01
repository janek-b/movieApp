package com.example.guest.movieapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.guest.movieapp.Constants;
import com.example.guest.movieapp.R;
import com.example.guest.movieapp.models.Movie;
import com.example.guest.movieapp.ui.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MovieSearchViewHolder> {
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private Context mContext;

    public MovieSearchAdapter(Context context, ArrayList<Movie> movies) {
        mMovies = movies;
        mContext = context;
    }


    @Override
    public MovieSearchAdapter.MovieSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_search_item, parent, false);
        MovieSearchViewHolder viewHolder = new MovieSearchViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MovieSearchAdapter.MovieSearchViewHolder holder, int position) {
        holder.bindMovieSearch(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


    public class MovieSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.moviePoster) ImageView mMoviePoster;
        @Bind(R.id.movieTitle) TextView mMovieTitle;
        @Bind(R.id.eachMovie) RelativeLayout mEachMovie;


        private Context mContext;

        public MovieSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindMovieSearch(Movie movie) {
            mMovieTitle.setText(movie.getTitle());
            Picasso.with(mContext).load(String.format("%s%s", Constants.IMAGE_URL, movie.getPoster_path())).into(mMoviePoster);
            if (getLayoutPosition() % 2 == 0) {
                mEachMovie.setBackgroundColor(0xFF00FF00);
            }

        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Intent intent = new Intent(mContext, MovieDetailActivity.class);
            intent.putExtra("movie", Parcels.wrap(mMovies.get(position)));
            mContext.startActivity(intent);

        }
    }
}
