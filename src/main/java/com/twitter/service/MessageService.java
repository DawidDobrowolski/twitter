package com.twitter.service;


import com.twitter.entity.Message;
import com.twitter.entity.User;
import com.twitter.repository.MessageRepository;
import com.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {

private UserRepository userRepository;
private MessageRepository messageRepository;

@Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }


    public User getUserById(Long id){
    return userRepository.findOne(id);
    }

    public void saveMessage(Message message, Long receiverId, Long senderId){
    message.setReceiver(userRepository.findOne(receiverId));
    message.setSender(userRepository.findOne(senderId));
    messageRepository.save(message);
    }




}
