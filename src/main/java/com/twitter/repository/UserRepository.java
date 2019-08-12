package com.twitter.repository;

import com.twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {


    User getFirstByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.firstName LIKE CONCAT('%',:search,'%') or u.lastName LIKE CONCAT('%',:search,'%')")
    List<User> customSearchUser(@Param("search") String search);


}
