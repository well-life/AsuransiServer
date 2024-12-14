package com.asuransi_akses.AsuransiServer.services;

import com.asuransi_akses.AsuransiServer.model.LogActivity;
import com.asuransi_akses.AsuransiServer.repository.LogActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogActivityService {

    @Autowired
    private LogActivityRepository logActivityRepository;

    public void log(String aktivitas, String username) {
        LogActivity log = new LogActivity();
        log.setAktivitas(aktivitas);
        log.setUsername(username);
        log.setWaktu(new Date());
        logActivityRepository.save(log);
    }
}
