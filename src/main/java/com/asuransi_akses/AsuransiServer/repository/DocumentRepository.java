package com.asuransi_akses.AsuransiServer.repository;

import com.asuransi_akses.AsuransiServer.model.CustomerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<CustomerFile, Long> {
}
