package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    List<User> getAllUsers();

    void updateUser(User user);

    User getUser(Long id);

    String getPassword(Long id);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User findByUsername(String username);

    void deleteById(Long id);

}



