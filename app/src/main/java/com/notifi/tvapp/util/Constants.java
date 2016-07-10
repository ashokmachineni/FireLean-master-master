package com.notifi.tvapp.util;

import com.notifi.tvapp.BuildConfig;

/**
 * Created by Олег on 30.05.2016.
 */
public class Constants {
    /**
     * Constants related to locations in Firebase, such as the name of the node
     * where movies are stored (ie "hindi_movies")
     */
    public static final String FIREBASE_LOCATION_ENGLISH_MOVIES = "english_movies";
    public static final String FIREBASE_LOCATION_HINDI_MOVIES = "hindi_movies";
    public static final String FIREBASE_LOCATION_TAMIL_MOVIES = "tamil_movies";
    public static final String FIREBASE_LOCATION_TELUGU_MOVIES = "telugu_movies";

    /**
     * Constants for Firebase object properties
     */
    public static final String FIREBASE_PROPERTY_TITLE = "title";
    public static final String FIREBASE_PROPERTY_DESCRIPTION = "description";
    public static final String FIREBASE_PROPERTY_VIDEO_URL = "videoUrl";
    public static final String FIREBASE_PROPERTY_STUDIO = "studio";
    public static final String FIREBASE_PROPERTY_CATEGORY = "category";
    public static final String FIREBASE_PROPERTY_CARD_IMAGE_URL = "cardImageUrl";
    public static final String FIREBASE_PROPERTY_BG_IMAGE_URL = "bgImageUrl";

    /**
     * Constants for Firebase URL
     */
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    public static final String FIREBASE_URL_ENGLISH_MOVIES = FIREBASE_URL + FIREBASE_LOCATION_ENGLISH_MOVIES;
    public static final String FIREBASE_URL_HINDI_MOVIES = FIREBASE_URL + "/" + FIREBASE_LOCATION_HINDI_MOVIES;
    public static final String FIREBASE_URL_TELUGU_MOVIES = FIREBASE_URL + "/" + FIREBASE_LOCATION_TELUGU_MOVIES;
    public static final String FIREBASE_URL_TAMIL_MOVIES = FIREBASE_URL + "/" + FIREBASE_LOCATION_TAMIL_MOVIES;


}
