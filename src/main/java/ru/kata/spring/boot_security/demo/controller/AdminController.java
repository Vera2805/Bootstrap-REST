package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;




@Controller
@RequestMapping ("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "")
    public String showAdminPage(Model model,Principal principal) {

        String username = principal.getName();
        User user = (User) userService.loadUserByUsername(username);
        Set<String> roleNames = user.getRoleNames();

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("username", username);
        model.addAttribute("roles", roleNames);
        return "admin-page";
    }



    @GetMapping(value = "/")
    public String getUsersForm(ModelMap model) {

        Set<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "redirect:/admin";
    }

    @GetMapping("/add")
    public String newUserForm(ModelMap model) {
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String viewUserPage (Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "user-page";
    }
    @PostMapping("/new")
    public String newUserPage(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
        }
        Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        model.addAttribute("roles", roleNames);
        model.addAttribute("username",user.getUsername());
        model.addAttribute("getRoles",roleNames);
        return "redirect:/admin";
    }


  @PostMapping("/edit{id}")
  public String editUserForm(ModelMap model, @PathVariable Long id) {
      User user = userService.getUser(id);
       Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
      model.put("user", user);
      model.put("getRoles", roleNames);
      model.addAttribute("roles", roleNames);
      return "redirect:/admin";
  }
/* @PostMapping("/edit/{id}")
 public String editUserForm(ModelMap model, @PathVariable Long id) {
     User user = userService.getUser(id);
     Set<Role> roles = user.getRoles();
     model.put("user", user);
     model.put("roles", roles);

     return "redirect:/admin";
 }*/

    @PatchMapping ("/user/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        Set<Role> roles = user.getRoles().stream().map(role -> roleService.findByName(role.getName()))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/admin{id}")
    public String deleteUserById(@RequestParam(required = true, defaultValue = "") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}







