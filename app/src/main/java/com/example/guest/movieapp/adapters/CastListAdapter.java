package com.example.guest.movieapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.movieapp.Constants;
import com.example.guest.movieapp.R;
import com.example.guest.movieapp.models.Movie;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 6/1/17.
 */

public class CastListAdapter extends RecyclerView.Adapter<CastListAdapter.CastListViewHolder> {
    private ArrayList<JsonObject> castMembers = new ArrayList<>();
    private Context mContext;

    public CastListAdapter(Context context, ArrayList<JsonObject> castMembers) {
        Log.d("adapter", "adapterConstructor");
        this.mContext = context;
        this.castMembers = castMembers;
    }


    @Override
    public CastListAdapter.CastListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("adapter", "oncreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        CastListViewHolder viewHolder = new CastListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CastListAdapter.CastListViewHolder holder, int position) {
        Log.d("adapter", "onbindviewholder");
        holder.bindCastMember(castMembers.get(position));
    }

    @Override
    public int getItemCount() {
        return castMembers.size();
    }

    public class CastListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.castName) TextView mCastName;
        @Bind(R.id.castCharacter) TextView mCastCharacter;
        @Bind(R.id.castImage) ImageView mCastImage;

        private Context mContext;

        public CastListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindCastMember(JsonObject cast) {
            Log.d("adapter", cast.toString());
            mCastName.setText(cast.get("name").toString());
            mCastCharacter.setText(cast.get("character").toString());
            Picasso.with(mContext).load(String.format("%s%s", Constants.IMAGE_URL, cast.get("profile_path").toString())).into(mCastImage);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
