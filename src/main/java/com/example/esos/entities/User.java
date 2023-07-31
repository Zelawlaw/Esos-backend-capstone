/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.esos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lawrencekarani
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "username", unique = true) //making username unique
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "manager")
    private Collection<User> directReports;
    @JoinColumn(name = "managerid", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private User manager;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserPermission userPermission;

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public User(String username, String password, PasswordEncoder passwordEncoder) {

        this.username = username;
        this.password = passwordEncoder.encode(password);
    }

    public User(String username, String password, User manager, PasswordEncoder passwordEncoder) {
        this.username = username;
        this.password = passwordEncoder.encode(password);
        this.manager = manager;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", directReports size =").append(directReports.size());
        sb.append(", manager=").append(manager);
        sb.append(", userPermission=").append(userPermission);
        sb.append('}');
        return sb.toString();
    }
}
