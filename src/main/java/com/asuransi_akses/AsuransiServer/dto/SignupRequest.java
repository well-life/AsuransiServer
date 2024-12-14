package com.asuransi_akses.AsuransiServer.dto;


import lombok.Data;

@Data
public class SignupRequest {
    private String nip;
    private String name;
    private String email;
    private String password;
    private String alamat;
    private String noHp;

}