package com.protectora.gatos.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtilities {

    private final String jwtSecretToken = "miClaveSecretaSuperSeguraParaLaProtectoraDeGatitos2024DebeSerMuyLargaYComplejaMasDe64Caracteres123456";
    
    private final int jwtExpirationMiliseconds = 86400000; //esto son 24 horas
    
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecretToken.getBytes());
    }
    
    public String generateJwtToken(Authentication authentication) {
        EmployeeDetailsSecurity userPrincipal = (EmployeeDetailsSecurity)authentication.getPrincipal();
        
        return Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMiliseconds))
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();
    }
    
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("El token no es válido: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("El token ha expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Token no soportado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Token vacío: " + e.getMessage());
        }
        return false;
    }
}
