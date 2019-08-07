package com.twitter.service;


import com.twitter.entity.Tweet;
import com.twitter.entity.User;
import com.twitter.repository.TweetRepository;
import com.twitter.repository.UserRepository;
import com.twitter.support.CurrentUser;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TweetService {

    private TweetRepository tweetRepository;
    private UserRepository userRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public List<Tweet> getAllTweets() {
        List<Tweet> tweets = tweetRepository.findAll();
        tweets = tweets.stream()
                .sorted((t1, t2) -> t2.getCreated().compareTo(t1.getCreated()))
                .collect(Collectors.toList());
        return tweets;
    }


    public List<Tweet> getAllTweetsByUser(Long id) {
        List<Tweet> tweets = tweetRepository.getAllByUserId(id);
        tweets = tweets.stream()
                .sorted((t1, t2) -> t2.getCreated().compareTo(t1.getCreated()))
                .collect(Collectors.toList());
        return tweets;
    }


    public void saveTweet(Tweet tweet, CurrentUser customUser) {
        tweet.setUser(customUser.getUser());
        tweetRepository.save(tweet);
    }

    public Tweet getTweetById(Long id) {
        return tweetRepository.findOne(id);
    }

    public Tweet getTweetByIdWithComments(Long id) {
        Tweet tweet = tweetRepository.findOne(id);
        Hibernate.initialize(tweet.getComments());
        tweet.setComments(tweet.getComments().stream()
                .sorted((t1, t2) -> t2.getCreated().compareTo(t1.getCreated()))
                .collect(Collectors.toList()));
        return tweet;
    }

    public void deleteTweet(Long id) {
        tweetRepository.delete(id);
    }

    public User getUserById(Long id){
        return userRepository.findOne(id);
    }
}
