package com.esisba.tp1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String data_center;


    @OneToMany(mappedBy = "primary_server")
    private Collection<Vm> primary_vms;

    @OneToMany(mappedBy = "backup_server")
    private Collection<Vm> backup_vms;

    @Embedded
    private Configuration configuration;
}
