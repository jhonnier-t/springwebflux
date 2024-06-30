package com.test.softka.services;


import com.test.softka.model.Audit;
import com.test.softka.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuditService  {

    @Autowired
    private AuditRepository auditRepository;

    public Mono<Audit> saveAuditEvent(Audit event) {
        return auditRepository.save(event);
    }
}
