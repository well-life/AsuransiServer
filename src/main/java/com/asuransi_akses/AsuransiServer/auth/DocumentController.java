package com.asuransi_akses.AsuransiServer.auth;

import com.asuransi_akses.AsuransiServer.dto.KlaimResponse;
import com.asuransi_akses.AsuransiServer.model.Customer;
import com.asuransi_akses.AsuransiServer.model.CustomerFile;
import com.asuransi_akses.AsuransiServer.model.Klaim;
import com.asuransi_akses.AsuransiServer.model.MstKontrak;
import com.asuransi_akses.AsuransiServer.repository.KlaimRepository;
import com.asuransi_akses.AsuransiServer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class DocumentController {

    private static final String UPLOAD_DIR = "D:\\download\\";

    @Autowired
    private DocumentService documentService;

    @Autowired
    private KlaimService klaimService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private KlaimRepository klaimRepository;

    @Autowired
    private LogActivityService logActivityService;

    @PostMapping("api/upload")
    public ResponseEntity<Map<String, String>> upload(
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "id-customer") String idCustomer,
            @RequestParam(value = "jenis-asuransi") String jenisAsuransi,
            @RequestParam(value = "id-contract") Long idKontrak,
            @RequestParam(value = "no-polis") Long noPolis,
            @RequestParam(value = "description", required = false) String description
    ) {
        try {
            // Log input untuk debugging

            // Validasi idCustomer
            if (idCustomer == null || idCustomer.isEmpty()) {
                return ResponseEntity.status(400).body(createErrorResponse("Customer ID is required"));
            }

            // Validasi customer existence
            Customer customer = customerService.getCustomerById(Long.parseLong(idCustomer));
            if (customer == null) {
                return ResponseEntity.status(404).body(createErrorResponse("Customer not found"));
            }

            // Validasi kontrak existence
            MstKontrak mstKontrak = contractService.getMstKontrak(idKontrak);
            if (mstKontrak == null) {
                return ResponseEntity.status(404).body(createErrorResponse("Contract not found"));
            }

            // Membuat klaim baru
            Klaim klaim = new Klaim();
            klaim.setJenisAsuransi(jenisAsuransi);
            klaim.setCreatedAt(new Date());
            klaim.setIdCustomer(customer);
            klaim.setIdKontrak(mstKontrak);
            klaim.setNoPolis(noPolis);
            klaim.setDescription(description);
            klaim.setStatus((byte) 2); // Status: In Review
            klaim = klaimService.saveKlaim(klaim);

            // Jika ada file, simpan ke sistem
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (file.isEmpty()) {
                        continue; // Skip jika file kosong
                    }

                    String originalFileName = file.getOriginalFilename();
                    if (originalFileName == null) {
                        System.out.println("File tanpa nama, tidak diproses.");
                        continue;
                    }

                    Long idMasterDoc = extractNumberFromFileName(originalFileName); // Extract ID dari nama file
                    if (idMasterDoc == null) {
                        System.out.println("ID Master Document tidak ditemukan dalam nama file.");
                        continue;
                    }

                    // Menyimpan file ke sistem penyimpanan
                    String fileName = saveFile(idCustomer, idMasterDoc, file);
                    documentService.saveCustomerFile(Long.parseLong(idCustomer), idMasterDoc, fileName, UPLOAD_DIR);
                    System.out.println("File saved: " + fileName);
                }
            } else {
                System.out.println("No files provided, claim created without document uploads.");
            }

            // Respons sukses
            return ResponseEntity.ok(createSuccessResponse("Claim uploaded successfully" +
                    (files != null && files.length > 0 ? " with files" : "")));

        } catch (Exception e) {
            // Menangani error dan memberikan respons yang sesuai
            e.printStackTrace();
            return ResponseEntity.status(500).body(createErrorResponse("Failed to upload files and create claim"));
        }
    }



    @GetMapping("api/claims/all")
    public ResponseEntity<?> getAllClaims() {
        try {
            List<Klaim> klaimList = klaimService.getAllKlaims();
            return ResponseEntity.ok(klaimList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(createErrorResponse("Failed to retrieve claims"));
        }
    }

    @GetMapping("/api/claims/{id}")
    public ResponseEntity<KlaimResponse> getClaimDetailsWithDocuments(@PathVariable Long id) {
        try {
            // Ambil detail klaim
            Klaim klaim = klaimService.findById(id);
            if (klaim == null) {
                return ResponseEntity.notFound().build();
            }

            // Ambil dokumen terkait berdasarkan idCustomer
            List<CustomerFile> dokumen = documentService.getFilesByClaimId(id);

            // Buat response DTO
            KlaimResponse response = new KlaimResponse(klaim, dokumen);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PatchMapping("api/claims/{id}/update-status")
    public ResponseEntity<?> updateClaimStatus(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            Byte newStatus = ((Number) payload.get("status")).byteValue();
            String notes = (String) payload.get("notes");  // Menangkap catatan dari payload

            Klaim klaim = klaimService.findById(id);

            if (klaim == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Claim not found"));
            }

            // Mengupdate status dan menambahkan catatan
            klaim.setStatus(newStatus);
            klaim.setNotes(notes);  // Menyimpan catatan yang diterima

            // Memperbarui klaim di database
            klaimService.updateClaimStatus(klaim);

            // Kembalikan respons sukses dengan klaim yang sudah diperbarui
            return ResponseEntity.ok(klaim);
        } catch (Exception e) {
            // Jika terjadi error lainnya, cetak stack trace dan kembalikan error 500
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Failed to update claim status"));
        }
    }


    @PatchMapping("api/claims/{id}/update")
    public ResponseEntity<Map<String, String>> updateClaim(
            @PathVariable Long id,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "id-customer") String idCustomer,
            @RequestParam(value = "jenis-asuransi") String jenisAsuransi,
            @RequestParam(value = "id-contract") Long idKontrak,
            @RequestParam(value = "description", required = false) String description

    ) {
        Map<String, String> response = new HashMap<>();

        try {
            // Ambil klaim berdasarkan ID
            Klaim klaim = klaimService.findById(id);
            if (klaim == null) {
                response.put("error", "Claim not found");
                return ResponseEntity.status(404).body(response);
            }

            // Update data klaim
            klaim.setJenisAsuransi(jenisAsuransi);
            klaim.setIdCustomer(customerService.getCustomerById(Long.parseLong(idCustomer)));
            klaim.setIdKontrak(contractService.getMstKontrak(idKontrak));
            klaim.setModifiedAt(new Date());
            klaim.setStatus((byte)2);
            klaim.setDescription(description);
            klaimService.updateKlaim(klaim);

            // Simpan file jika ada
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    Long idMasterDoc = extractNumberFromFileName(file.getOriginalFilename());
                    String fileName = saveFile(idCustomer, idMasterDoc, file);
                    documentService.saveCustomerFile(Long.parseLong(idCustomer), idMasterDoc, fileName, UPLOAD_DIR);
                }
            }

            // Kembalikan respons sukses dengan pesan
            response.put("message", "Claim updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Jika terjadi error lainnya, cetak stack trace dan kembalikan error 500
            e.printStackTrace();
            response.put("error", "Failed to update claim");
            return ResponseEntity.status(500).body(response);
        }
    }

    private String saveFile(String idCustomer, Long idMasterDoc, MultipartFile file) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String fileName = String.format("%s\\%s.jpeg", idCustomer, idMasterDoc);
        Path path = Paths.get(UPLOAD_DIR + fileName);
        File dir = new File(UPLOAD_DIR + idCustomer);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // Menyimpan file
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Jika gagal, hapus file yang telah disalin
            File fileToDelete = new File(UPLOAD_DIR + fileName);
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
            throw e;  // Melempar kembali exception agar dapat ditangani
        }
        return fileName;
    }

    private Map<String, String> createSuccessResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        return response;
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", message);
        return response;
    }

    public static Long extractNumberFromFileName(String fileName) {
        String[] parts = fileName.split("\\.");

        if (parts.length > 0) {
            try {
                return Long.parseLong(parts[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format in filename.");
            }
        }
        return null;
    }
}
