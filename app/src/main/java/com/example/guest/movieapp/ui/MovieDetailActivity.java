package com.example.guest.movieapp.ui;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.movieapp.Constants;
import com.example.guest.movieapp.R;
import com.example.guest.movieapp.adapters.CastListAdapter;
import com.example.guest.movieapp.models.Movie;
import com.example.guest.movieapp.services.MovieService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {
    private static Movie mMovie;
    private static ArrayList<JsonObject> castMembers = new ArrayList<>();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//
//        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);
        mMovie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        setTitle(mMovie.getTitle());


        getCast(mMovie.getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    public void setAdapters() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void getCast(int id) {
        disposable.add(MovieService.makeApiCall(MovieService.buildDetailUrl(Constants.CREDITS, id))
                .subscribeOn(Schedulers.io())
                .map(new Function<String, ArrayList<JsonObject>>() {
                    @Override public ArrayList<JsonObject> apply(String string) {
                        ArrayList<JsonObject> cast = new ArrayList<>();
                        JsonArray results = new Gson().fromJson(string, JsonObject.class).getAsJsonArray("cast");
                        for (JsonElement item : results) {
                            cast.add(new Gson().fromJson(item.toString(), JsonObject.class));
                        }
                        return cast;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<JsonObject>>() {
                    @Override public void onNext(@NonNull ArrayList<JsonObject> s) {
                        Log.d("test", "setting list");
                        castMembers = s;
                        setAdapters();
                    }
                    @Override public void onError(@NonNull Throwable e) { e.printStackTrace(); }
                    @Override public void onComplete() {}
                }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
                TextView titleText = (TextView) rootView.findViewById(R.id.text_title);
                titleText.setText(mMovie.getTitle());
                TextView releaseText = (TextView) rootView.findViewById(R.id.text_released);
                releaseText.setText(mMovie.getRelease_date());
                TextView overviewText = (TextView) rootView.findViewById(R.id.text_overview);
                overviewText.setText(mMovie.getOverview());
                ImageView moviePoster = (ImageView) rootView.findViewById(R.id.movie_poster);
                Picasso.with(rootView.getContext()).load(String.format("%s%s", Constants.IMAGE_URL, mMovie.getPoster_path())).into(moviePoster);
//                return rootView;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView = inflater.inflate(R.layout.fragment_movie_cast, container, false);
                RecyclerView castRecyclerView = (RecyclerView) rootView.findViewById(R.id.castResultView);
                Log.d("test", "setting adapter");
                Log.d("test", castMembers.toString());
                CastListAdapter adapter = new CastListAdapter(getActivity().getApplicationContext(), castMembers);
                castRecyclerView.setAdapter(adapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                castRecyclerView.setLayoutManager(layoutManager);
                castRecyclerView.setHasFixedSize(true);
//                disposable.add(MovieService.makeApiCall(MovieService.buildDetailUrl(Constants.CREDITS, mMovie.getId()))
//                        .subscribeOn(Schedulers.io())
//                        .map(new Function<String, ArrayList<JsonObject>>() {
//                            @Override public ArrayList<JsonObject> apply(String string) {
//                                ArrayList<JsonObject> cast = new ArrayList<>();
//                                JsonArray results = new Gson().fromJson(string, JsonObject.class).getAsJsonArray("cast");
//                                for (JsonElement item : results) {
//                                    cast.add(new Gson().fromJson(item.toString(), JsonObject.class));
//                                }
//                                return cast;
//                            }
//                        }).observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<ArrayList<JsonObject>>() {
//                            @Override public void onNext(@NonNull ArrayList<JsonObject> s) {
//                                Log.d("test", "inside subscription");
//                                CastListAdapter adapter = new CastListAdapter(getContext(), s);
//                                castRecyclerView.setAdapter(adapter);
//                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//                                castRecyclerView.setLayoutManager(layoutManager);
//                                castRecyclerView.setHasFixedSize(true);
//                            }
//                            @Override public void onError(@NonNull Throwable e) { e.printStackTrace(); }
//                            @Override public void onComplete() {}
//                        }));
//                return rootView;
            } else {
                rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
//                return rootView;
//                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            }
            Log.d("test", "returning rootView");
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {return 3;}

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Details";
                case 1:
                    return "Cast &\nCrew";
                case 2:
                    return "Similar\nMovies";
            }
            return null;
        }
    }
}
