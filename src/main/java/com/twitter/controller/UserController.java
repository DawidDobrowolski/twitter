package com.twitter.controller;

import com.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String showAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/user/list";
    }

    @GetMapping("/search")
    public String searchUser() {
        return "/user/search";
    }


    @PostMapping("/search")
    public String searchUser(@RequestParam String search,  Model model) {
        model.addAttribute("users",userService.searchAllUsers(search));
        return "/user/list";
    }



}
