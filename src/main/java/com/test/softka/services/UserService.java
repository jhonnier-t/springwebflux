package com.test.softka.services;

import com.test.softka.model.User;
import com.test.softka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean verifyAuthUser(User user) {
        Mono<User> userMono = getUserByEmail(user.getEmail());
        Mono<Boolean> statusMono = userMono.map(userCall -> userCall != null && userCall.getPassword().equals(user.getPassword()));
        return Boolean.TRUE.equals(statusMono.block());
    }

    public User updateUserSession(String email, boolean statusEnable) {
        User userResponse = null;
        User userToUpdate = getUserByEmail(email).block();
        if (userToUpdate != null) {
            userToUpdate.setEnabled(statusEnable);
            userResponse = userRepository.save(userToUpdate).block();
        }
        return userResponse;
    }

}
