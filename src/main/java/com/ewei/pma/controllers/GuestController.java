package com.ewei.pma.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guest")
public class GuestController {

    @GetMapping("/admin") 
    public String adminGuest() {
        return "security/guest";
    }

    @GetMapping("/user") 
    public String userGuest() {
        return "security/guest";
    }

}
