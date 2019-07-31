package com.twitter.repository;

import com.twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


    User getFirstByEmail(String email);
}
