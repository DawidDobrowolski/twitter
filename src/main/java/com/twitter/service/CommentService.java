package com.twitter.service;

import com.twitter.entity.Comment;
import com.twitter.repository.CommentRepository;
import com.twitter.repository.TweetRepository;
import com.twitter.support.CurrentUser;
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


    public void saveComment(Comment comment, Long tweetId, CurrentUser customUser ){
        comment.setTweet(tweetRepository.findOne(tweetId));
        comment.setUser(customUser.getUser());
        commentRepository.save(comment);
    }

    public Comment getCommentById(Long id){
        return commentRepository.findOne(id);
    }

    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }

}
