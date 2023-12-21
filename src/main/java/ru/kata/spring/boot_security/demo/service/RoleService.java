package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

public interface RoleService {
    Set<Role> indexRoles();


    Role getRoleByName(String roleName);


    Role getRoleById(Long id);

    void addRole(Role role);


    Role findByName(String roleName);


    void save(User user);

    void save(Role role);



}
