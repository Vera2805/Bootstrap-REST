package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    void addUser(User user);

    List<User> getAllUsers();

    User findByUsername(String username);

    void updateUser(User user);

    User getUser(Long id);

    String getPassword(Long id);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void deleteById(Long id);

}





