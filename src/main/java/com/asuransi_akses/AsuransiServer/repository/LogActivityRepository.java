package com.asuransi_akses.AsuransiServer.repository;

import com.asuransi_akses.AsuransiServer.model.LogActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogActivityRepository extends JpaRepository<LogActivity, Long> {
}
