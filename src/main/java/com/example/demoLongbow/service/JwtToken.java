package com.example.demoLongbow.service;

import com.example.demoLongbow.entity.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsMutator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtToken {
    @Autowired
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    private int JWT_VALID_TIME=10*1000;

    private Key generateKey(){
       return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Employee employee){
        Date date=new Date();
        Date expiry=new Date(date.getTime()+JWT_VALID_TIME);
        Map<String,Object>tokenData=new HashMap<>();
        tokenData.put("id",employee.getId());
        tokenData.put("name",employee.getName());
        tokenData.put("email",employee.getEmail());
        String token=Jwts.builder().claims().add(tokenData).and()
                .subject(employee.getEmail()).issuedAt(date).expiration(expiry).signWith(generateKey(),
                        SignatureAlgorithm.HS256).compact();
        return token;
    }

    public Claims getJwtClaims(String token){
        SecretKey secretKey=new SecretKeySpec(jwtSecretKey.getBytes(),"HmacShaHS256");
        Claims claims=Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
         return claims;
    }
    public Boolean verifyJwtToken(String token){
        Claims claims=getJwtClaims(token);
        Boolean isValid=claims.getExpiration().after(new Date());
        return isValid;
    }
}
