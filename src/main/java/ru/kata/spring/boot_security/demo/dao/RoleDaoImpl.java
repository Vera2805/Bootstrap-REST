package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> indexRoles() {
        return (Set<Role>) entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public Role getRoleByName(String roleName) {
        return entityManager
                .createQuery("select r from Role r where r.name =: name", Role.class)
                .setParameter("name", roleName)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role getAllRoleByNames() {
        return null;
    }

    @Override
    public Set<Role> findAll() {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r", Role.class);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public Set<Role> getRolesByIds(Set<Long> roleIds) {
        return new HashSet<>(entityManager
                .createQuery("select r from Role r where r.id in :ids", Role.class)
                .setParameter("ids", roleIds)
                .getResultList());
    }

    @Override
    public Role findByName(String roleName) {
        return entityManager.find(Role.class, roleName);
    }

}
