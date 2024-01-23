package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Lazy
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void addUser(User user, Set<Role> roles) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public Set<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public void updateUser(User user, Set<Role> roles) {
        User oldUser = userDao.getUser(user.getId());
        if (!oldUser.getPassword().equals(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        user.setRoles(roles);
        userDao.updateUser(user);
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public String getPassword(Long id) {
        return userDao.getPassword(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadUserByUsername(username);
    }

}
