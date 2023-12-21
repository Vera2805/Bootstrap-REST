package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUser(id);
        entityManager.remove(user);
    }

    @Override
    public Set<User> getAllUsers() {

        return new HashSet<>(entityManager.createQuery("select u from User u", User.class)
                .getResultList());
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public String getPassword(Long id) {
        return entityManager.find(User.class, id).getPassword();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TypedQuery<User> typedQuery = entityManager.createQuery(
                "select u from User u LEFT JOIN FETCH u.roles where u.username = '" + username + "'", User.class
        );
        User user = typedQuery.getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


    @Override
    public Role getRoleByName(String roleName) {
        TypedQuery<Role> typedQuery = entityManager.createQuery(
                "select r from Role r where r.name = '" + roleName + "'", Role.class);
        return typedQuery.getSingleResult();
    }

    @Override
    public void save(User user) {

    }

    @Override
    public User getUser()  {
        return entityManager.find(User.class,getUser());

    }

    @Override
    public User findUsername(String name) {
        return entityManager.find(User.class,name);
    }


}



