package com.asuransi_akses.AsuransiServer.services;

import com.asuransi_akses.AsuransiServer.model.Customer;
import com.asuransi_akses.AsuransiServer.model.CustomerFile;
import com.asuransi_akses.AsuransiServer.model.MasterDocument;
import com.asuransi_akses.AsuransiServer.repository.CustomerFileRepository;
import com.asuransi_akses.AsuransiServer.repository.CustomerRepository;
import com.asuransi_akses.AsuransiServer.repository.MasterDocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private CustomerFileRepository customerFileRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MasterDocumentRepository masterDocumentRepository;

    // Metode untuk menyimpan metadata file
    @Transactional
    public void saveCustomerFile(Long idCustomer, Long idMasterDoc, String fileName, String directoryPath) {
        CustomerFile customerFile = new CustomerFile();
        Customer c = new Customer();
        c.setIdCustomer(idCustomer);
        MasterDocument m = new MasterDocument();
        m.setId(idMasterDoc);

        Optional<CustomerFile> op = customerFileRepository.checkFileIsExists(idCustomer, idMasterDoc);
        if(op.isEmpty()){
            customerFile.setIdCustomer(c);
            customerFile.setIdMasterDoc(m);
            customerFile.setCreatedAt(new Date());
            customerFile.setCreatedBy("Imanuel");
            customerFile.setDirectory(directoryPath + fileName);
            customerFileRepository.save(customerFile);
        }else{
            CustomerFile cNext = op.get();
            cNext.setModifiedAt(new Date());
            cNext.setModifiedBy("Imanuel");
        }
    }

    public List<CustomerFile> getFilesByClaimId(Long idKlaim) {
        return customerFileRepository.findDocumentByIdKlaim(idKlaim);
    }

}
