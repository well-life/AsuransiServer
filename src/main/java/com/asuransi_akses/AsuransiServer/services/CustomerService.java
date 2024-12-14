package com.asuransi_akses.AsuransiServer.services;

import com.asuransi_akses.AsuransiServer.model.Customer;
import com.asuransi_akses.AsuransiServer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Mengambil Customer berdasarkan ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }
}
