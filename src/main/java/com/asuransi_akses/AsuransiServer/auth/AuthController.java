package com.asuransi_akses.AsuransiServer.auth;


import com.asuransi_akses.AsuransiServer.dto.AuthenticationRequest;
import com.asuransi_akses.AsuransiServer.dto.AuthenticationResponse;
import com.asuransi_akses.AsuransiServer.dto.SignupRequest;
import com.asuransi_akses.AsuransiServer.dto.UserDto;
import com.asuransi_akses.AsuransiServer.model.User;
import com.asuransi_akses.AsuransiServer.repository.UserRepository;
import com.asuransi_akses.AsuransiServer.services.auth.AuthService;
import com.asuransi_akses.AsuransiServer.services.jwt.UserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
//    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        try{
            UserDto createdUser = authService.createUser(signupRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (EntityExistsException entityExistsException){
            return new ResponseEntity<>("User Already exist", HttpStatus.NOT_ACCEPTABLE);
        }catch (Exception e){
            return new ResponseEntity<>("User not created, come again later", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        String usernameOrNip = authenticationRequest.getUsernameOrNip();
        String password = authenticationRequest.getPassword();

        try {
            Optional<User> userOptional = userRepository.findFirstByNip(usernameOrNip);
            if (!userOptional.isPresent()) {
                userOptional = userRepository.findFirstByEmail(usernameOrNip);
            }

            if (userOptional.isPresent()) {
//                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrNip, password));
            } else {
//                throw new BadCredentialsException("Incorrect username or nip");
            }

        } catch (Exception e) {
//            throw new Exception("Incorrect username or password");
        }

//        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(usernameOrNip);
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getUsernameOrNip());
//        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (optionalUser.isPresent()) {
//            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }

        return authenticationResponse;
    }
}

