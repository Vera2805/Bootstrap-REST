package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String getAdminPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            return "redirect:/logout";
        }
        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        model.addAttribute("username", principal.getName());
        model.addAttribute("role", roles);
        return "admin-page";
    }
}





