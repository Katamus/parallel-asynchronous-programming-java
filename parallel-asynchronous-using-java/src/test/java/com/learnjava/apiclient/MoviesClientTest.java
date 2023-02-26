package com.learnjava.apiclient;

import com.learnjava.util.CommonUtil;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoviesClientTest {

    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();

    MoviesClient  moviesClient = new MoviesClient(webClient);

    @RepeatedTest(10)
    void retrieveMovie(){
        CommonUtil.startTimer();
        //given
        var moviInfoId = 1L;

        //when
        var movie = moviesClient.retrieveMovie(moviInfoId);
        CommonUtil.timeTaken();
        //then
        assert movie != null;
        assertEquals("Batman Begins",movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;

    }


    @RepeatedTest(10)
    void retrieveMovies(){
        CommonUtil.startTimer();
        //given
        var movieInfoIds = List.of(1L,2L,3L,4L,5L,6L,7L);

        //when
        var movie = moviesClient.retrieveMovies(movieInfoIds);
        CommonUtil.timeTaken();
        //then
        assert movie != null;
        assert movie.size() == 7;

    }



    @RepeatedTest(10)
    void retrieveMovie_CF(){
        //given
        CommonUtil.startTimer();
        var moviInfoId = 1L;

        //when
        var movie = moviesClient.retrieveMovie_CF(moviInfoId).join();
        CommonUtil.timeTaken();
        //then
        assert movie != null;
        assertEquals("Batman Begins",movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;

    }

    @RepeatedTest(10)
    void retrieveMovieList_CF(){
        //given
        CommonUtil.startTimer();
        var movieInfoIds = List.of(1L,2L,3L,4L,5L,6L,7L);

        //when
        var movie = moviesClient.retrieveMovieList_CF(movieInfoIds);
        CommonUtil.timeTaken();
        //then
        assert movie != null;
        assert movie.size() == 7;

    }

    @RepeatedTest(10)
    void retrieveMovieList_CF_allof() {

        //given
        CommonUtil.startTimer();
        var movieInfoIds = List.of(1L,2L,3L,4L,5L,6L,7L);

        //when
        var movie = moviesClient.retrieveMovieList_CF_allof(movieInfoIds);
        CommonUtil.timeTaken();
        //then
        assert movie != null;
        assert movie.size() == 7;
    }
}