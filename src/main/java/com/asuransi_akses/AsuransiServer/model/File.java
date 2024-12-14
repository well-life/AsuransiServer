package com.asuransi_akses.AsuransiServer.model;//package com.asuransi_akses.AsuransiServer.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name = "File")
//@Data
//public class File {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "IDCustomer", nullable = false)
//    private Customer customer;
//
//    @Column(name = "IDFile", nullable = false)
//    private String idFile;
//
//    @Column(name = "DirectoryFile", nullable = false)
//    private String directoryFile;
//
//    @Column(name = "Url", nullable = false)
//    private String url;
//
//    @Column(name = "AuditTrace")
//    private String auditTrace;
//
//}
