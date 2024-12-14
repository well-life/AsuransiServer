package com.asuransi_akses.AsuransiServer.services.auth;


import com.asuransi_akses.AsuransiServer.dto.SignupRequest;
import com.asuransi_akses.AsuransiServer.dto.UserDto;
import com.asuransi_akses.AsuransiServer.enums.UserRole;
import com.asuransi_akses.AsuransiServer.model.User;
import com.asuransi_akses.AsuransiServer.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder;  // Memanfaatkan dependency injection

    @PostConstruct
    public void createAnAdminAccount() {
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);

        if (adminAccount.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setNip("ADMIN12345");  // Admin memiliki NIP
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword("admin");
            user.setNoHp("081234567890"); // Pastikan noHp di-set
            userRepository.save(user);
        } else {
            System.out.println("Admin Account already exist");
        }
    }


    public UserDto createUser(SignupRequest signupRequest) {
        // Validasi jika NIP sudah terdaftar di database
        if (userRepository.findFirstByNip(signupRequest.getNip()).isPresent()) {
            throw new EntityExistsException("User Already Present With NIP " + signupRequest.getNip());
        }
        User user = new User();
        user.setNip(signupRequest.getNip());  // Set NIP
        user.setEmail(signupRequest.getEmail());  // Set email
        user.setName(signupRequest.getName());  // Set name
        user.setUserRole(UserRole.CUSTOMER);  // Set role sebagai CUSTOMER
        user.setPassword(signupRequest.getPassword());  // Set password yang dienkripsi
        user.setAlamat(signupRequest.getAlamat());  //
        user.setNoHp(signupRequest.getNoHp());  //

        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();
    }

}



