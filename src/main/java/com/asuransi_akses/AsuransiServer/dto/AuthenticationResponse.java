package com.asuransi_akses.AsuransiServer.dto;

import com.asuransi_akses.AsuransiServer.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;
}
