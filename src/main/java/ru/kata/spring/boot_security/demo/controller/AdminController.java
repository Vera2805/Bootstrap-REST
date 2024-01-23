package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

;



@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String getAdminPage(Model model, Principal principal) {
        Set<String> roles = userService.findByUsername(principal.getName())
                .getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("username", principal.getName());
        model.addAttribute("role", roles);
        return "admin-page";
    }

    @GetMapping()
    public String getLoginPage() {
        return "redirect:/admin";
    }

    @PutMapping("/user/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("listRolesForUpdate") List<Long> roleIds, Principal principal) {
        String oldName = userService.getUser(user.getId()).getUsername();
        userService.updateUser(user, roleService.getRolesByIds(new HashSet<>(roleIds)));
        if (principal.getName().equals(oldName)) {
            return "redirect:/logout";
        }
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String getNewUserPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam("listRoles") List<Long> roleIds) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return "redirect:/admin";
        }
        userService.addUser(user, roleService.getRolesByIds(new HashSet<>(roleIds)));
        return "redirect:/admin";
    }

    @DeleteMapping("admin/{id}")
    public String deleteUserById(@PathVariable("id") Long id, Principal principal) {
        String oldName = userService.getUser(id).getUsername();
        userService.deleteById(id);
        if (principal.getName().equals(oldName)) {
            return "redirect:/logout";
        }
        return "redirect:/admin";
    }
}




