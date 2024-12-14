package com.asuransi_akses.AsuransiServer.auth;


import com.asuransi_akses.AsuransiServer.services.ContractService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController

public class ContractController {

    @Autowired
    ContractService contractService;

    @GetMapping("/contract/{idKontrak}")
    public ResponseEntity<Object> findByContract(@PathVariable(value = "idKontrak") Long idKontrak, HttpServletRequest request){
        return contractService.findById(idKontrak, request);
    }
}
