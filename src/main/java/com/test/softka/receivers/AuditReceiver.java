package com.test.softka.receivers;

// AuditReceiver.java
import com.test.softka.model.Audit;
import com.test.softka.repository.AuditRepository;
import com.test.softka.services.AuditService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditReceiver {

    @Autowired
    private AuditService auditService;

    public void receiveMessage(Audit audit) {
        System.out.println("Entra listener receiveMessage ");
        Audit auditSaved = auditService.saveAuditEvent(audit).block();
    }
}
