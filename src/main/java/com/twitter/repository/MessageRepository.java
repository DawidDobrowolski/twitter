package com.twitter.repository;

import com.twitter.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> getAllByReceiverId(Long id);

    List<Message> getAllBySenderId(Long id);

    @Query("SELECT m FROM Message m WHERE m.sender.id=:id OR m.receiver.id=:id")
    List<Message> customGetAllBySenderIdAndReceiverId(@Param("id") Long id);
}
