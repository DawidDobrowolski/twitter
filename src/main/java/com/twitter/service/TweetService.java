package com.twitter.service;


import com.twitter.entity.Tweet;
import com.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TweetService {

    private TweetRepository tweetRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public List<Tweet> getAllTweets() {
        List<Tweet> tweets = tweetRepository.findAll();
        tweets = tweets.stream()
                .sorted((t1, t2) -> t2.getCreated().compareTo(t1.getCreated()))
                .collect(Collectors.toList());
        return tweets;
    }

    public void saveTweet(Tweet tweet) {
        tweetRepository.save(tweet);
    }


}
