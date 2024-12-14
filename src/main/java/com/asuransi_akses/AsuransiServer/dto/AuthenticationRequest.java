package com.asuransi_akses.AsuransiServer.dto;


import lombok.Data;

@Data
public class AuthenticationRequest {
    private String usernameOrNip;
    private String password;
}