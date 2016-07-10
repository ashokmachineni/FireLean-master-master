package com.notifi.tvapp;

import com.notifi.tvapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public final class MovieList {
    public static final String MOVIE_CATEGORY[] = {
            "TELUGU MOVIES",
            "HINDI MOVIES",
            "TAMIL MOVIES",
            "ENGLISH MOVIES",
            "Category Four",
            "Category Five",
    };

    public static List<Movie> list;

    public static List<Movie> setupMovies() {
        list = new ArrayList<Movie>();
        String title[] = {
                "Srimanthudu",
                "Express Raja",
                "Fan",
                "AirLift",
                "Azhar"
        };

        String description = "Watch Srimanthudu (2015) DVDRip Telugu Full Movie Online Free "
                + "Watch Express Raja (2016) DVDScr Hindi Telugu Full Movie Online Free"
                + "Watch Fan (2016) DVDScr Hindi Full Movie Online Free "
                + "Watch Fan (2016) DVDScr Hindi Full Movie Online Free "
                + "Watch AirLift (2016) DVDScr Hindi Full Movie Online Free "
                + "Watch Azhar (2016) DVDScr Hindi Full Movie Online Free";

        String videoUrl[] = {
                "http://afterdj.com/movie/sriman.mp4",
                "http://afterdj.com/movie/express.mp4",
                "http://www.mirchilive.com/wp-content/uploads/2016/05/fan.mp4",
                "http://afterdj.com/movie/airlift.mp4",
                "http://www.mirchilive.com/wp-content/uploads/2016/05/azhar.mp4"
        };
        String bgImageUrl[] = {
                "http://www.movierulz.to/wp-content/uploads/2016/05/Srimanthudu-New-Image.jpg",
                "http://www.movierulz.to/wp-content/uploads/2016/01/Express-Raja-Image.jpg",
                "http://www.movierulz.to/wp-content/uploads/2016/04/Fan-Image.jpg",
                "http://www.movierulz.to/wp-content/uploads/2016/03/Airlift-Image.jpg",
                "http://www.movierulz.to/wp-content/uploads/2016/05/Azhar-Poster.jpg",
        };
        String cardImageUrl[] = {
                "http://www.movierulz.to/wp-content/uploads/2016/05/Srimanthudu-New-Image.jpg",
                "http://www.movierulz.to/wp-content/uploads/2016/01/Express-Raja-Image.jpg",
                "http://www.movierulz.to/wp-content/uploads/2016/04/Fan-Image.jpg",
                "http://www.movierulz.to/wp-content/uploads/2016/03/Airlift-Image.jpg",
                "http://www.movierulz.to/wp-content/uploads/2016/05/Azhar-Poster.jpg"
        };

        list.add(buildMovieInfo("category", title[0],
                description, "Telugu Movie", videoUrl[0], cardImageUrl[0], bgImageUrl[0]));
        list.add(buildMovieInfo("category", title[1],
                description, "ASHOK IPTV", videoUrl[1], cardImageUrl[1], bgImageUrl[1]));
        list.add(buildMovieInfo("category", title[2],
                description, "ASHOK IPTV", videoUrl[2], cardImageUrl[2], bgImageUrl[2]));
        list.add(buildMovieInfo("category", title[3],
                description, "ASHOK IPTV", videoUrl[3], cardImageUrl[3], bgImageUrl[3]));
        list.add(buildMovieInfo("category", title[4],
                description, "ASHOK IPTV", videoUrl[4], cardImageUrl[4], bgImageUrl[4]));

        return list;
    }

    private static Movie buildMovieInfo(String category, String title,
                                        String description, String studio, String videoUrl, String cardImageUrl,
                                        String bgImageUrl) {
        Movie movie = new Movie();
//        movie.setId(Movie.getCount());
//        Movie.incCount();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setStudio(studio);
        movie.setCategory(category);
        movie.setCardImageUrl(cardImageUrl);
        movie.setBgImageUrl(bgImageUrl);
        movie.setVideoUrl(videoUrl);
        return movie;
    }
}
