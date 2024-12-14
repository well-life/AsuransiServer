package com.asuransi_akses.AsuransiServer.services;

import com.asuransi_akses.AsuransiServer.model.CustomerFile;
import com.asuransi_akses.AsuransiServer.model.MstKontrak;
import com.asuransi_akses.AsuransiServer.repository.ContractRepository;
import com.asuransi_akses.AsuransiServer.repository.CustomerFileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContractService {


    @Autowired
    private CustomerFileRepository customerFileRepo;

    @Autowired
    private ContractRepository contractRepo;

    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<MstKontrak> optionalAkses = contractRepo.findById(id);
        if(!optionalAkses.isPresent()) {
            return null;
        }
        MstKontrak mstKontrak = optionalAkses.get();
        System.out.println(String.valueOf(mstKontrak.getIdCustomer().getIdCustomer()));
        List<CustomerFile> l = customerFileRepo.cariFile(String.valueOf(mstKontrak.getIdCustomer().getIdCustomer()));

        Map<String, Object> map = new HashMap<>();
        map.put("dataKontrak", mstKontrak);
        map.put("dataCustomerFile", l);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    public MstKontrak getMstKontrak(Long idKontrak) {
        return contractRepo.findById(idKontrak).orElse(null);
    }

}
