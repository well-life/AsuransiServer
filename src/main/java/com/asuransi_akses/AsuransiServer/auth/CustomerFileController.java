package com.asuransi_akses.AsuransiServer.auth;

import com.asuransi_akses.AsuransiServer.services.DocumentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("file")
@CrossOrigin(origins = "*")
public class CustomerFileController {

    private DocumentService documentService;

    @GetMapping("/{id}")
    public void findById(@PathVariable(value = "id") Long id){
    }

//    @GetMapping("/api/claims/{idKlaim}")
//    public List<CustomerFile> getCustomerFilesByProductAndCustomer(
//            @PathVariable Long idKlaim) {
//        return documentService.getFilesByClaimId(idKlaim);
//    }
}
