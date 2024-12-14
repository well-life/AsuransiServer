package com.asuransi_akses.AsuransiServer.dto;

import com.asuransi_akses.AsuransiServer.model.CustomerFile;
import com.asuransi_akses.AsuransiServer.model.Klaim;

import java.util.List;

public class KlaimResponse {
    private Klaim klaim;
    private List<CustomerFile> dokumen;

    // Constructor
    public KlaimResponse(Klaim klaim, List<CustomerFile> dokumen) {
        this.klaim = klaim;
        this.dokumen = dokumen;
    }

    // Getters dan Setters
    public Klaim getKlaim() {
        return klaim;
    }

    public void setKlaim(Klaim klaim) {
        this.klaim = klaim;
    }

    public List<CustomerFile> getDokumen() {
        return dokumen;
    }

    public void setDokumen(List<CustomerFile> dokumen) {
        this.dokumen = dokumen;
    }
}

