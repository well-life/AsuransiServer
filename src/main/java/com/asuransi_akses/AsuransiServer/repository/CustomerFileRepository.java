package com.asuransi_akses.AsuransiServer.repository;

import com.asuransi_akses.AsuransiServer.model.CustomerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerFileRepository extends JpaRepository<CustomerFile, Long> {

    @Query(value = "SELECT x FROM CustomerFile x WHERE x.idCustomer.idCustomer = ?1")
    List<CustomerFile> cariFile(String custId);

    @Query(value = "SELECT x FROM CustomerFile x WHERE x.idCustomer.idCustomer = ?1 AND x.idMasterDoc.id = ?2")
    Optional<CustomerFile> checkFileIsExists(Long custId, Long idFile);

    @Query(value = "SELECT cf FROM CustomerFile cf " +
            "JOIN cf.idCustomer c " +  // Hubungkan CustomerFile dengan Customer
            "JOIN Klaim k ON k.idCustomer = c " + // Hubungkan Customer dengan Klaim
            "WHERE k.id = ?1") // Filter berdasarkan idKlaim
    List<CustomerFile> findDocumentByIdKlaim(Long idKlaim);
}
