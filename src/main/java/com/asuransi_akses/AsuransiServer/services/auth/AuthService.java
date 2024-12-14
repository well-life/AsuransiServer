package com.asuransi_akses.AsuransiServer.services.auth;


import com.asuransi_akses.AsuransiServer.dto.SignupRequest;
import com.asuransi_akses.AsuransiServer.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
}
