package com.test.softka.controllers;

import com.test.softka.model.Audit;
import com.test.softka.model.User;
import com.test.softka.services.AuditService;
import com.test.softka.services.UserService;
import com.test.softka.utils.enums.EventEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AuditService auditService;

    @Autowired
    private UserService userService;

    private final RabbitTemplate rabbitTemplate;

    public AuthController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody User user) {
        Audit auditEvent = new Audit(null, user.getEmail(), EventEnum.LOGIN.getValor(), LocalDateTime.now());
        rabbitTemplate.convertAndSend("auditExchange", "auditRoutingKey", auditEvent);
        boolean statusAuth = userService.verifyAuthUser(user);
        if (statusAuth){
            return Mono.just("Login successful");
        }
        return Mono.just("Login denied");
    }

    @PostMapping("/logout")
    public Mono<String> logout(@RequestBody User user) {
        rabbitTemplate.convertAndSend(queueName,
                new Audit(null, user.getEmail(), EventEnum.LOGOUT.getValor(), LocalDateTime.now()));
        return null;
    }
}


