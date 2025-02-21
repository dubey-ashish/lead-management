package com.udaan.leads.security.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("accounts")
public class SystemUser {
    @Id
    private String id;
    private String username;
    private String password;
    private List<SystemRoles> systemRoles;
}