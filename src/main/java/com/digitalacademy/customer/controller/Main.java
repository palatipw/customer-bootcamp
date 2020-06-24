package com.digitalacademy.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;

class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        UserController userController = new UserController(userService);
        System.out.println(userController.getUser());
    }
}

class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    public String getUser(){
        UserService userService = new UserService();
        return userService.getUser();
    }
}

class UserService {
    public String getUser(){
        return "Ryan";
    }
}