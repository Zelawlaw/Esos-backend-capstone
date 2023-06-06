/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.esos.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author lawrencekarani
 */
@Entity
@Table(name = "incidents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "incidentid")
    private String incidentID;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "reportedtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportedtime;
    @Column(name = "reporter")
    private String reporter;
    @Column(name = "incidentowner")
    private String incidentowner;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL  ,fetch = FetchType.EAGER)
    private Collection<Log> logsCollection;


    public Incident( String incidentID,String  description, Date reportedtime,String reporter){
        this.incidentID=incidentID;
        this.description=description;
        this.reportedtime=reportedtime;
        this.reporter=reporter;

    }
    
}
