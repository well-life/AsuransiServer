package com.asuransi_akses.AsuransiServer.repository;

import com.asuransi_akses.AsuransiServer.model.Customer;
import com.asuransi_akses.AsuransiServer.model.Klaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KlaimRepository extends JpaRepository<Klaim, Long> {

    @Query(value = "SELECT x FROM Klaim x WHERE x.idCustomer.idCustomer = ?1 AND x.idKontrak.idKontrak = ?2 AND x.noPolis = ?3 AND x.status IN (3, 4)")
    Optional<Klaim> cekMultiKlaim(String custId, String idContract, Long noPolis);


    List<Klaim> findByIdCustomer(Customer customer);

    List<Klaim> findAll();

    Optional<Klaim> findById(Long id);
}

