package com.twitter.service;


import com.twitter.entity.Role;
import com.twitter.entity.User;
import com.twitter.repository.RoleRepository;
import com.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;;


@Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User findByUserEmail(String email) {
        return userRepository.getFirstByEmail(email);
    }


    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.getFirstByName("ROLE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
