package com.twitter.controller;

import com.twitter.entity.User;
import com.twitter.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {


    private LoginService loginService;


    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "/login/login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "/login/register";
    }

    @PostMapping("/register")
    public String saveRegistrationForm(@Valid User user, BindingResult result, @RequestParam String confirmPassword) {
        if (result.hasErrors()) {
            return "login/register";
        }

        if (!user.getPassword().equals(confirmPassword)) {
            result.addError(new FieldError("user", "password", "passwords do not match"));
            return "login/register";
        }

        if (loginService.getUserByEmail(user.getEmail()) != null) {
            result.addError(new FieldError("user", "email", "user already exist"));
            return "login/register";
        }

        loginService.saveUser(user);
        return "redirect:/login/login";
    }




//    @PostMapping("/login")
//    public String logged(){
//        return "redirect:/twitter";
//    }

}
