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

    @PostMapping("/send/{receiverId}")
    public String sendMessage(Model model,@Valid Message message, BindingResult result, @AuthenticationPrincipal CurrentUser customUser, @PathVariable Long receiverId) {
        if (result.hasErrors()) {
            model.addAttribute("receiver", messageService.getUserById(receiverId));
            return "message/send";
        }
        if (customUser.getUser().getId()!=receiverId) {
            messageService.saveMessage(message, receiverId, customUser.getUser().getId());
        }
        return "redirect:/message/talk/" + receiverId;
    }

    @GetMapping("/inbox")
    public String showInbox(Model model, @AuthenticationPrincipal CurrentUser customUser) {
        model.addAttribute("messages", messageService.getInboxMessages(customUser.getUser().getId()));
        model.addAttribute("mailbox","Inbox");
        return "/message/mailbox";
    }

    @GetMapping("/outbox")
    public String showOutbox(Model model, @AuthenticationPrincipal CurrentUser customUser) {
        model.addAttribute("messages", messageService.getOutboxMessages(customUser.getUser().getId()));
        model.addAttribute("mailbox","Outbox");
        return "/message/mailbox";
    }

    @GetMapping("/talk/{id}")
    public String showTalk(Model model, @PathVariable Long id,@AuthenticationPrincipal CurrentUser customUser) {
        if(customUser.getUser().getId()==id){
            return "redirect:/message/inbox";
        }
        model.addAttribute("receiver", messageService.getUserById(id));
        model.addAttribute("message", new Message());
        model.addAttribute("messages", messageService.getTalkMessages(id,customUser.getUser().getId()));
        return "/message/talk";
    }

    @GetMapping("/details/{id}")
    public String showDetails(Model model, @PathVariable Long id, @AuthenticationPrincipal CurrentUser customUser) {
        model.addAttribute("message", messageService.readMessageById(id,customUser.getUser().getId()));
        return "/message/details";
    }
}
