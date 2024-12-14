package com.asuransi_akses.AsuransiServer.repository;


import com.asuransi_akses.AsuransiServer.enums.UserRole;
import com.asuransi_akses.AsuransiServer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserRole(UserRole userRole);
    Optional<User> findFirstByNip(String nip);
    Optional<User> findFirstByEmail(String email);
}
