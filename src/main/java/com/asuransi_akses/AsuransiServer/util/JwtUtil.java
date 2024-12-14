package com.asuransi_akses.AsuransiServer.util;//package com.asuransi_akses.AsuransiServer.util;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component
//public class JwtUtil {
//    private String generateToken(Map<String, Object> extraClaims, UserDetails details){
//        return Jwts.builder().setClaims(extraClaims).setSubject(details.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*24))
//                .signWith(SignatureAlgorithm.HS256, getSigningKey()).compact();
//
//    }
//
//    public String generateToken(UserDetails userDetails){
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails){
//        final String userName = extractUserName(token);
//        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    private Claims extractAllClaims(String token){
//        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
//    }
//
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
//        final Claims claims = extractAllClaims(token);
//        return claimsResolvers.apply(claims);
//    }
//
//    public String extractUserName(String token){
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    private Date extractExpiration(String token){
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private boolean isTokenExpired(String token){
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Key getSigningKey(){
//        byte[] keyBytes = Decoders.BASE64.decode("4c2aee0c3e1b2d8c1e3d1c8e5f8b9a7c3f2e0c2a6f8d4c3e2c5a7f0b1e8d2e4c");
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//}
