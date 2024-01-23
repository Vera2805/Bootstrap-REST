package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
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
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> resultList = query.getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public void deleteById(Long id) {
        User user = getUser(id);
        entityManager.remove(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        User user = entityManager.createQuery(jpql, User.class)
                .setParameter("username", username)
                .getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}





