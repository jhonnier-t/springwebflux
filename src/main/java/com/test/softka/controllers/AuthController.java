package com.test.softka.controllers;

import com.test.softka.model.Audit;
import com.test.softka.model.User;
import com.test.softka.services.AuditService;
import com.test.softka.services.UserService;
import com.test.softka.utils.enums.EventEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/softka/auth")
public class AuthController {

    static final String queueName = "auditQueue";

    @Autowired
    private UserService userService;

    private final RabbitTemplate rabbitTemplate;

    public AuthController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<Mono<String>> login(@RequestBody User user) {
        Audit auditEvent = new Audit(null, user.getEmail(), EventEnum.LOGIN.getValor(), LocalDateTime.now());
        rabbitTemplate.convertAndSend(queueName, auditEvent);
        boolean statusAuth = userService.verifyAuthUser(user);
        if (statusAuth){
            userService.updateUserSession(user.getEmail(), true);
            return new ResponseEntity<>(Mono.just("Login successful"), HttpStatus.OK);
        }
        return new ResponseEntity<>(Mono.just("Login denied"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<Mono<String>> logout(@RequestBody User user) {
        Audit auditEvent = new Audit(null, user.getEmail(), EventEnum.LOGOUT.getValor(), LocalDateTime.now());
        rabbitTemplate.convertAndSend(queueName, auditEvent);
        User UserUpdated = userService.updateUserSession(user.getEmail(), false);
        if (UserUpdated != null){
            return new ResponseEntity<>(Mono.just("Logout successful"), HttpStatus.OK);
        }
        return new ResponseEntity<>(Mono.just("Logout denied"), HttpStatus.BAD_REQUEST);
    }
}


