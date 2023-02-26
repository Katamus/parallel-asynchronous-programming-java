package com.learnjava.apiclient;

import com.learnjava.util.CommonUtil;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

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

}