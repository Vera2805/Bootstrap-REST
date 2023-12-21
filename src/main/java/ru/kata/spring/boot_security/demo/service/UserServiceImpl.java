package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Lazy
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.addUser(user);

    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public Set<User> getAllUsers() {

        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User oldUser = userDao.getUser(user.getId());
        if (!oldUser.getPassword().equals(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
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
    @Override
    public void updateRole(String updateRoleForm) {

    }

    @Override
    public void updateRole(Role r) {

    }

    @Override
    public void save(User user) {

    }
    @Override
    public void save (Role role) {

    }


    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(Long id, User user) {

    }

  // @Override
   // public User getUser() {
  //      return userDao.getUser(getUser().getId());
  //  }



    @Override
    public void update(User user) {

    }

    @Override
    public User findUsername(String username) {
        return userDao.findUsername(username);
    }



}
