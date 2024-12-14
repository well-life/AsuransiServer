package com.asuransi_akses.AsuransiServer.repository;

import com.asuransi_akses.AsuransiServer.model.MstKontrak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<MstKontrak, Long> {

}
