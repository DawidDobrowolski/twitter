package com.twitter.service;

import com.twitter.entity.Comment;
import com.twitter.entity.Tweet;
import com.twitter.repository.CommentRepository;
import com.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

    private CommentRepository commentRepository;
    private TweetRepository tweetRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, TweetRepository tweetRepository) {
        this.commentRepository = commentRepository;
        this.tweetRepository = tweetRepository;
    }


    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    public Tweet getTweetById(Long id){
        return tweetRepository.findOne(id);
    }

    public Comment getCommentById(Long id){
        return commentRepository.findOne(id);
    }

    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }

}
