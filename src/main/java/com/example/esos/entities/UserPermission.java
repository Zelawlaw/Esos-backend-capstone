package com.example.esos.entities;


import com.example.esos.models.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "user_permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;


    @OneToOne
    @JoinColumn(name = "id")
    @JsonBackReference
    private User user;

    public UserPermission(Role role, User user) {
        this.role = role;
        this.user = user;
    }


}
