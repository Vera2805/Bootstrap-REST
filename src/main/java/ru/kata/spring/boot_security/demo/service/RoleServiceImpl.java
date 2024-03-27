package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleRepo;

    public RoleServiceImpl(RoleDao roleRepo) {
        this.roleRepo = roleRepo;
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
    public Set<Role> getRolesByIds(Set<Long> ids) {
        return roleRepo.getRolesByIds(ids);
    }

    @Override
    public Role getRoleById(Long id) {
        return null;
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
    public Set<String> getAllRoleNames() {
        return (Set<String>) roleRepo.getAllRoleByNames();
    }

    @Override
    public Set<Role> findAll() {
        return roleRepo.findAll();
    }
}
