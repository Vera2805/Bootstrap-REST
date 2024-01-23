package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Set;

@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initializeData() {
        Role userRole = new Role("ROLE_USER");
        roleService.addRole(userRole);
        User user = new User("imya", "fam", "mail@mail.com", "q", "q");
        userService.addUser(user, Set.of(userRole));

        Role adminRole = new Role("ROLE_ADMIN");
        roleService.addRole(adminRole);
        User admin = new User("im", "fami", "mail@mail.com", "admin", "admin");
        userService.addUser(admin, Set.of(adminRole, userRole));

    }


}

