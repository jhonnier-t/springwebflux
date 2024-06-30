package com.test.softka.listener;

import com.test.softka.model.Audit;
import com.test.softka.services.AuditService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

@Service
public class AuditListener {

    @Autowired
    private AuditService auditService;

    @RabbitListener(queues = "auditQueue")
    public void handleAuditEvent(Audit event) {
        Audit audit = auditService.saveAuditEvent(event).block();
    }
}
