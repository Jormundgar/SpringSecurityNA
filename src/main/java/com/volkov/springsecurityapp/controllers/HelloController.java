package com.volkov.springsecurityapp.controllers;

import com.volkov.springsecurityapp.security.UsersDetails;
import com.volkov.springsecurityapp.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HelloController {

    private final AdminService adminService;

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/userInfo")
    @ResponseBody
    public String showUserInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var usersDetails = (UsersDetails) authentication.getPrincipal();
        return usersDetails.getUsername();
    }

    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStuff();
        return "admin";
    }
}
