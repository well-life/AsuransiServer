package com.asuransi_akses.AsuransiServer.repository;


import com.asuransi_akses.AsuransiServer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByIdCustomer(Long id);
}