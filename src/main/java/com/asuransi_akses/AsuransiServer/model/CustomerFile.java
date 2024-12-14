package com.asuransi_akses.AsuransiServer.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "MapCustomerFile", uniqueConstraints = @UniqueConstraint(columnNames = {"IdMasterDoc", "IdCustomer"}))
@Data
public class CustomerFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IdMasterDoc")
    private MasterDocument idMasterDoc;

    @ManyToOne
    @JoinColumn(name = "IdCustomer")
    private Customer idCustomer;

    @Column(name = "CreatedBy",updatable=false,nullable=false)
    private String createdBy;

    @CreationTimestamp
    private Date createdAt;

    @Column(name = "ModifiedBy", insertable = true, updatable = true)
    private String modifiedBy;

    @Column(name = "ModifiedAt")
    @UpdateTimestamp
    private Date modifiedAt;

    @Column(name = "Directory")
    private String directory;

}
