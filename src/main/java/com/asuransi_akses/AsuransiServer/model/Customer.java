package com.asuransi_akses.AsuransiServer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Customer")
@Data
public class Customer {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCustomer")
    private Long idCustomer;

    @Column(name = "NamaCustomer", nullable = false)
    private String namaCustomer;

    @Column(nullable = false)
    private String alamat;

    @Column(name = "NoTelepon", nullable = false)
    private String noTelepon;

    @Column(nullable = false, unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "TanggalLahir")
    private Date tanggalLahir;

    @Column(name = "JenisKelamin")
    private String jenisKelamin;

    @Column(name = "NoKtp", nullable = false, unique = true)
    private String noKtp;
}
