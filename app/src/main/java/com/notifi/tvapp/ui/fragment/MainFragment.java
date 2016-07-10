/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.notifi.tvapp.ui.fragment;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.notifi.tvapp.adapter.MoviesAdapter;
import com.notifi.tvapp.model.CardPresenter;
import com.notifi.tvapp.MovieList;
import com.notifi.tvapp.R;
import com.notifi.tvapp.model.Movie;
import com.notifi.tvapp.ui.activity.BrowseErrorActivity;
import com.notifi.tvapp.ui.activity.DetailsActivity;
import com.notifi.tvapp.util.Constants;

public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int NUM_ROWS = 6;
    private static final int NUM_COLS = 15;

    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mRowsAdapter;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private URI mBackgroundURI;
    private BackgroundManager mBackgroundManager;

    private MoviesAdapter mHindiMoviesAdapter;
    private MoviesAdapter mTeluguMoviesAdaper;
    private MoviesAdapter mEnglishMoviesAdaper;
    private MoviesAdapter mTamilMoviesAdaper;

    static List<Movie> englishMovies = new ArrayList<>();
    static List<Movie> teluguMovies = new ArrayList<>();
    static List<Movie> hindiMovies = new ArrayList<>();
    static List<Movie> tamilMovies = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);

        prepareBackgroundManager();

        setupUIElements();

//        loadRows();

        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer) {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
            mBackgroundTimer.cancel();
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        final Firebase englishMoviesRef = new Firebase(Constants.FIREBASE_URL_ENGLISH_MOVIES);
        englishMoviesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Movie movie = dataSnapshot.getValue(Movie.class);
                    englishMovies.add(movie);
                    Log.e(TAG, movie.toString());
                Log.e(TAG, "englishmovies.count="+englishMovies.size());
                loadRows();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Movie movie = dataSnapshot.getValue(Movie.class);
//                Log.e(TAG, movie.toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase hindiMoviesRef = new Firebase(Constants.FIREBASE_URL_HINDI_MOVIES);
        hindiMoviesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Movie movie = dataSnapshot.getValue(Movie.class);
                hindiMovies.add(movie);
                Log.e(TAG, movie.toString());
                Log.e(TAG, "hindimovies.count="+hindiMovies.size());
                loadRows();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase tamilMoviesRef = new Firebase(Constants.FIREBASE_URL_TAMIL_MOVIES);
        tamilMoviesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Movie movie = dataSnapshot.getValue(Movie.class);
                tamilMovies.add(movie);
                Log.e(TAG, movie.toString());
                Log.e(TAG, "tamilmovies.count="+tamilMovies.size());
                loadRows();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase teluguMoviesRef = new Firebase(Constants.FIREBASE_URL_TELUGU_MOVIES);
        teluguMoviesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Movie movie = dataSnapshot.getValue(Movie.class);
                teluguMovies.add(movie);
                Log.e(TAG, movie.toString());
                Log.e(TAG, "telugumovies.count="+teluguMovies.size());
                loadRows();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mEnglishMoviesAdaper = new MoviesAdapter(getActivity(),Movie.class, android.R.layout.simple_list_item_1,englishMoviesRef );
        mTeluguMoviesAdaper = new MoviesAdapter(getActivity(),Movie.class, android.R.layout.simple_list_item_1,teluguMoviesRef);
        mTamilMoviesAdaper = new MoviesAdapter(getActivity(),Movie.class, android.R.layout.simple_list_item_1,tamilMoviesRef);
        mHindiMoviesAdapter = new MoviesAdapter(getActivity(),Movie.class, android.R.layout.simple_list_item_1,hindiMoviesRef);



        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void loadRows() {
            Log.e(TAG, "loadRows()");

//        for (int i=0;i<mEnglishMoviesAdaper.getCount();i++){
//            englishMovies.add(mEnglishMoviesAdaper.getItem(i));
//        }
//        for (int i=0;i<mTeluguMoviesAdaper.getCount();i++){
//            teluguMovies.add(mTeluguMoviesAdaper.getItem(i));
//        }
//        for (int i=0;i<mHindiMoviesAdapter.getCount();i++){
//            hindiMovies.add(mHindiMoviesAdapter.getItem(i));
//        }
//        for (int i=0;i<mTamilMoviesAdaper.getCount();i++){
//            tamilMovies.add(mTamilMoviesAdaper.getItem(i));
//        }
        Log.e(TAG, "englishmovies count="+englishMovies.size());
        Log.e(TAG, "hindimovies count="+hindiMovies.size());
//        Log.e(TAG, "englishmovies count="+englishMovies.size());
//        Log.e(TAG, "englishmovies count="+englishMovies.size());

//        List<Movie> list = MovieList.setupMovies();

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();

        //English movies
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (Movie movie : englishMovies){
            listRowAdapter.add(movie);
        }
        HeaderItem header = new HeaderItem(0, "ENGLISH MOVIES");
        mRowsAdapter.add(new ListRow(header, listRowAdapter));

        //Hindi movies
        listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (Movie movie : hindiMovies){
            listRowAdapter.add(movie);
        }
        header = new HeaderItem(1, "HINDI MOVIES");
        mRowsAdapter.add(new ListRow(header, listRowAdapter));

        //Telugu movies
        listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (Movie movie : teluguMovies){
            listRowAdapter.add(movie);
        }
        header = new HeaderItem(2, "TELUGU MOVIES");
        mRowsAdapter.add(new ListRow(header, listRowAdapter));

        //Tamil movies
        listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (Movie movie : tamilMovies){
            listRowAdapter.add(movie);
        }
        header = new HeaderItem(3, "TAMIL MOVIES");
        mRowsAdapter.add(new ListRow(header, listRowAdapter));
//        int i;
//        for (i = 0; i < NUM_ROWS; i++) {
//            if (i != 0) {
//                Collections.shuffle(list);
//            }
//            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
//            for (int j = 0; j < NUM_COLS; j++) {
//                listRowAdapter.add(list.get(j % 5));
//            }
//            HeaderItem header = new HeaderItem(i, MovieList.MOVIE_CATEGORY[i]);
//            mRowsAdapter.add(new ListRow(header, listRowAdapter));
//        }

        HeaderItem gridHeader = new HeaderItem(4, "PREFERENCES");

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getResources().getString(R.string.grid_view));
        gridRowAdapter.add(getString(R.string.error_fragment));
        gridRowAdapter.add(getResources().getString(R.string.personal_settings));
        mRowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));

        setAdapter(mRowsAdapter);

    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground = getResources().getDrawable(R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }

    private void setupEventListeners() {
        setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    protected void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        mBackgroundManager.setDrawable(resource);
                    }
                });
        mBackgroundTimer.cancel();
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        DetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            } else if (item instanceof String) {
                if (((String) item).indexOf(getString(R.string.error_fragment)) >= 0) {
                    Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Movie) {
                mBackgroundURI = ((Movie) item).getBackgroundImageURI();
                startBackgroundTimer();
            }

        }
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI.toString());
                    }
                }
            });

        }
    }

    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }

}
