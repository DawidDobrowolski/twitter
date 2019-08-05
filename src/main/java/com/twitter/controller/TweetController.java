package com.twitter.controller;


import com.twitter.entity.Tweet;
import com.twitter.entity.User;
import com.twitter.service.TweetService;
import com.twitter.support.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/twitter")
public class TweetController {

    private TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("")
    public String showAll(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        User entityUser = customUser.getUser();
        model.addAttribute("userId", entityUser.getId());
        model.addAttribute("tweets", tweetService.getAllTweets());
        return "/tweet/all";
    }

    @GetMapping("/add")
    public String addTweet(Model model) {
        model.addAttribute("tweet", new Tweet());
        return "/tweet/add";
    }

    @PostMapping("/add")
    public String addTweet(@Valid Tweet tweet, BindingResult result,@AuthenticationPrincipal CurrentUser customUser) {
        if (result.hasErrors()) {
            return "tweet/add";
        }
        if(tweet.getUser().getId() == null || tweet.getUser().getId() == customUser.getUser().getId()){
            tweet.setUser(customUser.getUser());
            tweetService.saveTweet(tweet);
        }
        return "redirect:/twitter";
    }

    @GetMapping("/edit/{id}")
    public String editTweet(Model model, @PathVariable Long id,@AuthenticationPrincipal CurrentUser customUser) {
        Tweet tweet = tweetService.getTweetById(id);
        if(tweet.getUser().getId() != customUser.getUser().getId()){
            return "redirect:/twitter";
        }
        model.addAttribute("tweet", tweet);
        return "tweet/add";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,@AuthenticationPrincipal CurrentUser customUser) {
        Tweet tweet = tweetService.getTweetById(id);
        if(tweet.getUser().getId() == customUser.getUser().getId()){
            tweetService.deleteTweet(id);
        }
        return "redirect:/twitter";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id,@AuthenticationPrincipal CurrentUser customUser) {
        User entityUser = customUser.getUser();
        model.addAttribute("userId", entityUser.getId());
        Tweet tweet = tweetService.getTweetById(id);
        model.addAttribute("tweet", tweet);
        return "tweet/details";
    }

}