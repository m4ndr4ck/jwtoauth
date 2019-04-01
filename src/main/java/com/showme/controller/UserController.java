package com.showme.controller;

import com.showme.model.User;
import com.showme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value ="/admin", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }

    @RequestMapping(value ="/standard", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public @ResponseBody String userArea(){
        return "User Area";
    }

    @RequestMapping(value ="/public", method = RequestMethod.GET)
    public @ResponseBody String publicArea(){
        return "Public Area";
    }

}
