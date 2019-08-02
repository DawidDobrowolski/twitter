package com.twitter.controller;


import com.twitter.entity.Role;
import com.twitter.entity.User;
import com.twitter.repository.RoleRepository;
import com.twitter.service.UserService;
import com.twitter.support.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    private UserService userService;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public HomeController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }



    @GetMapping("/")
    public String homePage(){
        User user =new User();
        user.setFirstName("Dawid");
        user.setLastName("Dobrowolski");
        user.setEmail("dawidek66@gmail.com");

        user.setPassword("dddddd");

        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userService.saveUser(user);

        return "index";
    }

    @GetMapping("/twitter")
    @ResponseBody
    public String admin(@AuthenticationPrincipal CurrentUser customUser) {
        User entityUser = customUser.getUser();
        return "this is user id " +entityUser.getId() ;
    }
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }


}
