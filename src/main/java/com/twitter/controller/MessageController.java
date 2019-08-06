package com.twitter.controller;


import com.twitter.entity.Message;
import com.twitter.entity.User;
import com.twitter.service.MessageService;
import com.twitter.support.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/send/{id}")
    public String sendMessage(Model model, @PathVariable Long id) {
        User receiver = messageService.getUserById(id);
        model.addAttribute("receiver", receiver);
        model.addAttribute("message", new Message());
        return "/message/send";
    }

    @PostMapping("/send/{id}")
    public String sendMessage(@Valid Message message, BindingResult result, @AuthenticationPrincipal CurrentUser customUser, @PathVariable Long id) {
        if (result.hasErrors()) {
            return "message/add";
        }
        messageService.saveMessage(message, id, customUser.getUser().getId());
        return "redirect:/twitter";
    }


}
