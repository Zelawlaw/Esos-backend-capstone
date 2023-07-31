/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.esos.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lawrencekarani
 */
@Entity
@Table(name = "logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "incidentupdate")
    private String incidentUpdate;
    //
//    @Column(name = "incidentid")
//    private String incidentId;
    @Column(name = "updatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;
    @Column(name = "updatedby")
    private String updatedby;

    // @JsonIgnore
    @JsonBackReference
    @JoinColumn(name = "incidentid", referencedColumnName = "incidentid")
    @ManyToOne
    private Incident incident;

    public Log(String incidentUpdate, Date updatetime, String updatedby) {
        this.incidentUpdate = incidentUpdate;
        this.updatetime = updatetime;
        this.updatedby = updatedby;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Log{");
        sb.append("id=").append(id);
        sb.append(", incidentUpdate='").append(incidentUpdate).append('\'');
        sb.append(", updatetime=").append(updatetime);
        sb.append(", updatedby='").append(updatedby).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
