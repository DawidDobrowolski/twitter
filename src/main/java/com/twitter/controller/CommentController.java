package com.twitter.controller;

import com.twitter.entity.Comment;
import com.twitter.service.CommentService;
import com.twitter.support.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
       commentService.saveComment(comment, tweetId, customUser);
        return "redirect:/twitter/details/" + tweetId;
    }

    @GetMapping("/edit/{tweetId}/{id}")
    public String editTweet(Model model, @PathVariable Long tweetId, @PathVariable Long id,@AuthenticationPrincipal CurrentUser customUser) {
        Comment comment = commentService.getCommentById(id);
        if(comment.getUser().getId() != customUser.getUser().getId()){
            return "redirect:/twitter/details/" + tweetId;
        }
        model.addAttribute("tweetId", tweetId);
        model.addAttribute("comment", comment);
        return "comment/add";
    }

    @GetMapping("/delete/{tweetId}/{id}")
    public String delete(@PathVariable Long id,@PathVariable Long tweetId,@AuthenticationPrincipal CurrentUser customUser) {
        Comment comment = commentService.getCommentById(id);
        if(comment.getUser().getId() == customUser.getUser().getId()){
            commentService.deleteComment(comment);
        }
        return "redirect:/twitter/details/" + tweetId;
    }


}
