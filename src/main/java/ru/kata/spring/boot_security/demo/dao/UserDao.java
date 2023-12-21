package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

public interface UserDao {
   //   User findByEmail(String email);


    void addUser(User user);

    void deleteUser(Long id);

    Set<User> getAllUsers();

    void updateUser(User user);

    User getUser(Long id);

    String getPassword(Long id);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Role getRoleByName(String roleName);

    void save(User user);


    User getUser();


    User findUsername(String username);
}




