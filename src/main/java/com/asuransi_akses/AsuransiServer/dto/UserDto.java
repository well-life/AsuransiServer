package com.asuransi_akses.AsuransiServer.dto;

import com.asuransi_akses.AsuransiServer.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String nip;
    private String email;
    private String name;
    private UserRole userRole;
}
