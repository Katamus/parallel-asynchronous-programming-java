package com.learnjava.apiclient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MoviesClient {

    private final WebClient webClient;


    public MoviesClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Movie retrieveMovie(Long movieInfoId){

        //movieInfo
        var movieInfo = invokeMovieInfoService(movieInfoId);
        //review
        var reviews = invokeReviewsService(movieInfoId);

        return new Movie(movieInfo,reviews);
    }

    public CompletableFuture<Movie> retrieveMovie_CF(Long movieInfoId){

        //movieInfo
        var movieInfo = CompletableFuture.supplyAsync(() -> invokeMovieInfoService(movieInfoId));

        //review
        var reviews =  CompletableFuture.supplyAsync(() -> invokeReviewsService(movieInfoId));

        return movieInfo.thenCombine(reviews,(movieInfo1, reviews1) -> new Movie(movieInfo1,reviews1));

    }

    private List<Review> invokeReviewsService(Long movieInfoId) {

        var reviewUri = UriComponentsBuilder.fromUriString("/v1/reviews")
                .queryParam("movieInfoId", movieInfoId)
                .buildAndExpand()
                .toString();


        return webClient
                .get()
                .uri(reviewUri)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .block();

    }

    private MovieInfo invokeMovieInfoService(Long movieInfoId) {

        var moviesInfoUrlPath = "/v1/movie_infos/{movieInfoId}";
        return webClient
                .get()
                .uri(moviesInfoUrlPath,movieInfoId)

                .retrieve()
                .bodyToMono(MovieInfo.class)
                .block();

    }


}
