package com.test.softka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "audit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Audit {
    private String id;
    private String user;
    private String event;
    private LocalDateTime eventDate;
}
