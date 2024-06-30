package com.test.softka.repository;

import com.test.softka.model.Audit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends ReactiveMongoRepository<Audit, String> {

}
