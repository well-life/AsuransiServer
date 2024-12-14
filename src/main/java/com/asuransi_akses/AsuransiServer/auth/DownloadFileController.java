package com.asuransi_akses.AsuransiServer.auth;


import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DownloadFileController {
    private String [] strExceptionArr = new String[2];
    private StringBuilder sBuild = new StringBuilder();
    private ServletOutputStream os;

    @GetMapping("api/download/{customerId}/{fileId}")
    public ResponseEntity<InputStreamResource> download(
            @PathVariable(value = "customerId") String custId,
            @PathVariable(value = "fileId") String fileId,
            HttpServletResponse response) throws FileNotFoundException {
        // Path file berdasarkan ID customer dan file
        String filePath = String.format("D:\\download\\%s\\%s.jpeg", custId, fileId);
        File file = new File(filePath);

        // Cek apakah file ada
        if (!file.exists()) {
            return ResponseEntity.status(404).body(null); // File tidak ditemukan
        }

        // Menggunakan InputStreamResource untuk mengirim file sebagai stream
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        // Menyiapkan header respons
        HttpHeaders headers = new HttpHeaders();
        String fileName = String.format("%s_%s.jpeg", fileId, new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // Menggunakan MediaType.IMAGE_JPEG untuk file gambar
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length()) // Menggunakan ukuran file yang benar
                .contentType(MediaType.IMAGE_JPEG) // Menggunakan type gambar JPEG
                .body(resource);
    }



}
