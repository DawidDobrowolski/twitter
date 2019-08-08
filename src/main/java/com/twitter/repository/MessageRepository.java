package com.twitter.repository;

import com.twitter.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> getAllByReceiverId(Long id);

    List<Message> getAllBySenderId(Long id);

    @Query("SELECT m FROM Message m WHERE (m.sender.id=:id and m.receiver.id=:myId) OR (m.receiver.id=:id and m.sender.id=:myId)")
    List<Message> customGetAllBySenderIdAndReceiverId(@Param("id") Long id,@Param("myId") Long myId);
}
