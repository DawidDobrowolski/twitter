package com.twitter.support;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

public class CurrentUser extends User {

    private final com.twitter.entity.User user;

    public CurrentUser(String email, String password, Collection<?
            extends GrantedAuthority> authorities,
                       com.twitter.entity.User user) {
        super(email, password, authorities);
        this.user = user;
    }

    public com.twitter.entity.User getUser() {return user;}
}
