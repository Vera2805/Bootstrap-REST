package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collections;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleRepo;

    private final UserDao userDao;

    public RoleServiceImpl(RoleDao roleRepo, UserDao userDao) {
        this.roleRepo = roleRepo;
        this.userDao = userDao;
    }

    @Override
    public Set<Role> indexRoles() {
        return roleRepo.indexRoles();

    }

    @Override
    public Role getRoleByName(String roleName) {

        return roleRepo.findByName(roleName);
    }


    @Override
    public Role getRoleById(Long id) {
        return roleRepo.getRoleById(id);
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        roleRepo.save(role);
    }


    @Override
    public Role findByName(String roleName) {
        return roleRepo.getRoleByName(roleName);
    }


    @Override
    public void save(User user) {
        Role role = new Role("ROLE_ADMIN");
        roleRepo.save(role);
        user.setRoles(Collections.singleton(role));
        userDao.save(user);

    }

    @Override
    public void save(Role role) {

    }



}
