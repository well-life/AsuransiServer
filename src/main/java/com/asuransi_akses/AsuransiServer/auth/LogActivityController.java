package com.asuransi_akses.AsuransiServer.auth;


import com.asuransi_akses.AsuransiServer.services.LogActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class LogActivityController {

    @Autowired
    private LogActivityService logActivityService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/log")
    public ResponseEntity<Map<String, String>> logAktivitas(@RequestParam String aktivitas, @RequestParam String username) {
        logActivityService.log(aktivitas, username);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Log berhasil disimpan");

        return ResponseEntity.ok(response);
    }

}
