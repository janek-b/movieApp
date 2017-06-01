package com.example.guest.movieapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.movieapp.Constants;
import com.example.guest.movieapp.R;
import com.example.guest.movieapp.models.Movie;
import com.squareup.picasso.Picasso;

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

        }

        @Override
        public void onClick(View v) {

        }
    }
}
