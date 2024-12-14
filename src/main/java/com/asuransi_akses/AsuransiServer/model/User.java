package com.asuransi_akses.AsuransiServer.model;

import com.asuransi_akses.AsuransiServer.dto.UserDto;
import com.asuransi_akses.AsuransiServer.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "[User]")
@Data
//public class User implements UserDetails {

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nip;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String alamat;

    @Column(name = "no_hp", nullable = false)
    private String noHp;

    //userRole = 0 (admin)
    //userRole = 1 (user)

    private UserRole userRole;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(userRole.name()));
//    }

//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

    public UserDto getUserDto() {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setUserRole(userRole);
        dto.setNip(nip);
        return dto;
    }
}
