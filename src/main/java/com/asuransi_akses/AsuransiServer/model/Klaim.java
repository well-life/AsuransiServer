package com.asuransi_akses.AsuransiServer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
//@Table(name = "Klaim", uniqueConstraints = @UniqueConstraint(columnNames = {"idCustomer", "noContract"}))
@Table(name = "Klaim")
@Data
public class Klaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDKlaim")
    private Long id;

    @Column(name = "JenisAsuransi", nullable = false)
    private String jenisAsuransi;

    @Column(name = "CreatedAt")
    private Date createdAt;

    @Column(name = "NoPolis", unique = true)
    private Long noPolis;

    @Column(name = "Status")
    private Byte status;

    @Column(name = "ModifiedAt")
    private Date modifiedAt;

    //5 = reject
    //4 = approve
    //3 = pending
    //2 = inReview
    //1 = onProgress

    @ManyToOne
    @JoinColumn(name = "IdCustomer")
    private Customer idCustomer;

    @ManyToOne
    @JoinColumn(name = "IdKontrak")
    private MstKontrak idKontrak;

    @Column(name = "Notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    public void setIdContract(MstKontrak idKontrak) {
        this.idKontrak = idKontrak;
    }

}
