package it.globus.finaudit.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.globus.finaudit.entity.Role;
import it.globus.finaudit.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private final int jwtExpirationMs;
    private final Algorithm algorithm;

    @Autowired
    public JwtUtils(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.expirationMs}") int jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
        this.algorithm = Algorithm.HMAC512(secret);
    }

    public String generateJwtToken(UserDetailsImpl userDetails) {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("id_user",userDetails.getUser().getId())
                .withClaim("username", userDetails.getUsername())
                .withClaim("authorities", userDetails.getUser().getRole().name())
                .withIssuer("finaudit")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .sign(algorithm);
    }

    public DecodedJWT   verifyJwtToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(algorithm)
                .withSubject("User Details")
                .withIssuer("finaudit")
                .build();
        return verifier.verify(token);
    }

    public String retrieveClaimUsername(DecodedJWT jwt)  {
        return jwt.getClaim("username").asString();
    }

    public Role retrieveClaimAuthority(DecodedJWT jwt)  {
        return jwt.getClaim("authorities").as(Role.class);
    }
    public Long retrieveClaimUserId(DecodedJWT jwt)  {
        return jwt.getClaim("id_user").asLong();
    }
}
