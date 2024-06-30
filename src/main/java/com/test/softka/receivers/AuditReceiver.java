package com.test.softka.receivers;

import com.test.softka.model.Audit;
import com.test.softka.services.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditReceiver {

    @Autowired
    private AuditService auditService;

    public void receiveMessage(Audit audit) {
        System.out.println("Entry listener: receiveMessage");
        auditService.saveAuditEvent(audit).block();
    }
}
