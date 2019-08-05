package com.twitter.controller;

import com.twitter.entity.Comment;
import com.twitter.entity.Tweet;
import com.twitter.service.CommentService;
import com.twitter.support.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/add/{tweetId}")
    public String addComment(Model model, @PathVariable Long tweetId) {
        model.addAttribute("tweetId", tweetId);
        model.addAttribute("comment", new Comment());
        return "/comment/add";
    }

    @PostMapping("/add/{tweetId}")
    public String addTweet(@Valid Comment comment, BindingResult result, @AuthenticationPrincipal CurrentUser customUser, @PathVariable Long tweetId) {
        if (result.hasErrors()) {
            return "comment/add";
        }
        comment.setTweet(commentService.getTweetById(tweetId));
        comment.setUser(customUser.getUser());
        commentService.saveComment(comment);
        return "redirect:/twitter/details/" + tweetId;
    }

}
