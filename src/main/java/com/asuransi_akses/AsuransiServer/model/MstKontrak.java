package com.asuransi_akses.AsuransiServer.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "MstKontrak")
@Data
public class MstKontrak {

    @Id
    @Column(name = "IDKontrak")
    private Long idKontrak;

    @Column(name = "Keterangan")
    private String keterangan;

    @Column(name = "NamaKendaraan")
    private String namaKendaraan;

    @Column(name = "NoPolisi", unique = true)
    private String nomorPolisi;

    @Column(name = "NoPolis", unique = true)
    private Long noPolis;

    @ManyToOne
    @JoinColumn(name = "IdCustomer")
    private Customer idCustomer;

}
