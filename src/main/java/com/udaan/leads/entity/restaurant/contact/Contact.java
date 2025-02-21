package com.udaan.leads.entity.restaurant.contact;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

@Data
public class Contact {
    private String name;
    private String employeeId;      //to avoid : [same role - same name] people

    private Role role;
    private List<ContactInfo> contacts;
}

