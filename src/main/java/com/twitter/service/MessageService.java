package com.twitter.service;


import com.twitter.entity.Message;
import com.twitter.entity.User;
import com.twitter.repository.MessageRepository;
import com.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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


    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    public void saveMessage(Message message, Long receiverId, Long senderId) {
        message.setReceiver(userRepository.findOne(receiverId));
        message.setSender(userRepository.findOne(senderId));
        messageRepository.save(message);
    }


    public List<Message> getInboxMessages(Long id) {
        List<Message> messages = messageRepository.getAllByReceiverId(id);
        messages = messages.stream()
                .sorted((m1, m2) -> m2.getCreated().compareTo(m1.getCreated()))
                .collect(Collectors.toList());
        return messages;
    }

    public List<Message> getOutboxMessages(Long id) {
        List<Message> messages = messageRepository.getAllBySenderId(id);
        messages = messages.stream()
                .sorted((m1, m2) -> m2.getCreated().compareTo(m1.getCreated()))
                .collect(Collectors.toList());
        return messages;
    }

    public List<Message> getTalkMessages(Long id) {
        List<Message> messages = messageRepository.customGetAllBySenderIdAndReceiverId(id);
        messages = messages.stream()
                .sorted((m1, m2) -> m1.getCreated().compareTo(m2.getCreated()))
                .collect(Collectors.toList());
        return messages;
    }

    public Message readMessageById(Long id){
        Message message = messageRepository.findOne(id);
        message.setReaded(true);
        return message;
    }
}
