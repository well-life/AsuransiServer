package com.asuransi_akses.AsuransiServer.services;

import com.asuransi_akses.AsuransiServer.model.Customer;
import com.asuransi_akses.AsuransiServer.model.Klaim;
import com.asuransi_akses.AsuransiServer.repository.KlaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KlaimService {

    @Autowired
    private KlaimRepository klaimRepository;

    public Klaim saveKlaim(Klaim klaim) {
        String jenisAsuransi = klaim.getJenisAsuransi();
        Optional<Klaim> existingKlaim = null;

        // Logika untuk asuransi kendaraan
        if ("asuransiKendaraan".equals(jenisAsuransi)) {
            // Cek klaim kendaraan berdasarkan customer, kontrak, dan nomor polis
            existingKlaim = klaimRepository.cekMultiKlaim(
                    String.valueOf(klaim.getIdCustomer().getIdCustomer()),
                    String.valueOf(klaim.getIdKontrak().getIdKontrak()),
                    klaim.getNoPolis()
            );

            if (existingKlaim.isPresent()) {
                // Ambil klaim lama yang ditemukan
                Klaim klaimLama = existingKlaim.get();

                // Periksa status klaim lama
                if (klaimLama.getStatus() == 4 || klaimLama.getStatus() == 5) {
                    // Status valid untuk mengajukan klaim baru
                    return klaimRepository.save(klaim);
                } else {
                    // Status tidak valid, klaim baru tidak boleh diajukan
                    return null;
                }
            }

            // Jika belum ada klaim kendaraan sebelumnya, simpan klaim baru
            return klaimRepository.save(klaim);
        }

        // Logika untuk jenis asuransi lainnya
        // Cek klaim berdasarkan customer, kontrak, dan no polis
        existingKlaim = klaimRepository.cekMultiKlaim(
                String.valueOf(klaim.getIdCustomer().getIdCustomer()),
                String.valueOf(klaim.getIdKontrak().getIdKontrak()),
                klaim.getNoPolis()
        );

        // Jika klaim sudah ada, periksa statusnya
        if (existingKlaim.isPresent()) {
            Klaim klaimLama = existingKlaim.get();

            // Pastikan jenis asuransi berbeda
            if (!klaimLama.getJenisAsuransi().equals(jenisAsuransi)) {
                return klaimRepository.save(klaim);
            }

            // Jika status klaim sebelumnya adalah 1, 2, 3, atau 4, klaim baru tidak boleh diajukan
            if (klaimLama.getStatus() == 1 || klaimLama.getStatus() == 2 || klaimLama.getStatus() == 3 || klaimLama.getStatus() == 4) {
                return null;
            }
        }

        // Simpan klaim baru jika belum ada klaim sebelumnya atau klaim sudah selesai
        return klaimRepository.save(klaim);
    }





    public Klaim updateClaimStatus(Klaim klaim) {
        return klaimRepository.save(klaim); // Simpan klaim setelah status diperbarui
    }

    // Method untuk mendapatkan klaim berdasarkan customer
    public List<Klaim> getKlaimsByCustomer(Customer customer) {
        return klaimRepository.findByIdCustomer(customer);
    }

    // Method untuk mendapatkan semua klaim
    public List<Klaim> getAllKlaims() {
        return klaimRepository.findAll(); // Mengambil semua klaim
    }

    public Klaim findById(Long id) {
        return klaimRepository.findById(id).orElse(null);
    }

    public Klaim updateKlaim(Klaim klaim) {
        // save akan memperbarui data jika ID sudah ada
        return klaimRepository.save(klaim);
    }

}
