package com.twitter.repository;

import com.twitter.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {


    Role getFirstByName(String name);
}
