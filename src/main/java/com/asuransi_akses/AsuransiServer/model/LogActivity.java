package com.asuransi_akses.AsuransiServer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "LogActivity")
public class LogActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aktivitas", nullable = false)
    private String aktivitas;

    @Column(name = "username", nullable = false) // Ubah dari "user" menjadi "username"
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "waktu", nullable = false, updatable = false)
    private Date waktu;
}
