package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

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
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Set<Role> getRolesByIds(Set<Integer> roleIds) {
        return (Set<Role>) entityManager
                .createQuery("select r from Role r where r.id in :ids", Role.class)
                .setParameter("ids", roleIds)
                .getResultList();
    }

    @Override
    public Role findByName(String roleName) {
        return entityManager.find(Role.class, roleName);
    }


}
