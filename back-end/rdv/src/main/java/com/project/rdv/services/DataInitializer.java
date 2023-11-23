package com.project.rdv.services;

import com.project.rdv.models.entity.User;
import com.project.rdv.models.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.project.rdv.security.Role.ADMIN;

@Component
public class DataInitializer {
    private final UserRepository userRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initData() {
        if (userRepository.findByUsername("DefaultUser") == null) {
            User user = new User();
            user.setUsername("DefaultUser");
            String hashedPassword = new BCryptPasswordEncoder().encode("DefaultPassword");
            user.setPassword(hashedPassword);
            user.setRole(ADMIN);
            user.setSector("TI");

            userRepository.save(user);
        }
    }
}
