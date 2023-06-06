/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.esos.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import lombok.AllArgsConstructor;
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
    @OneToMany(mappedBy = "incident")
    private Collection<Log> logsCollection;


    public Incident( String incidentID,String  description, Date reportedtime,String reporter){
        this.incidentID=incidentID;
        this.description=description;
        this.reportedtime=reportedtime;
        this.reporter=reporter;

    }
    
}