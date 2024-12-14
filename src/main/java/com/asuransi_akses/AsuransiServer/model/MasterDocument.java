package com.asuransi_akses.AsuransiServer.model;


import jakarta.persistence.*;
import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "MstDocument")
@Data

public class MasterDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "JenisDokumen", nullable = false, unique = true)
    private String jenisDokumen;


}
