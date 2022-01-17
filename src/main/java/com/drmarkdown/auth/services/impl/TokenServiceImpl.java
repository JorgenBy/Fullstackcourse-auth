package com.drmarkdown.auth.services.impl;

import com.drmarkdown.auth.exceptions.InvalidTokenException;
import com.drmarkdown.auth.models.MarkdownUserModel;
import com.drmarkdown.auth.services.AuthSigningKeyResolver;
import com.drmarkdown.auth.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    AuthSigningKeyResolver authSigningKeyResolver;

    @Override
    public void validateToken(String jwtToken) throws InvalidTokenException {
        try {

            Jwts.parserBuilder()
                    .setSigningKeyResolver(authSigningKeyResolver)
                    .build()
                    .parse(jwtToken);

        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid token", e);
        }
    }

    @Override
    public void generateToken(MarkdownUserModel markdownUserModel) {
        String jwtToken;

        jwtToken = Jwts.builder()
                    .setSubject(markdownUserModel.getUsername())
                    .setAudience(markdownUserModel.getRoles().toString())
                    .setIssuer(markdownUserModel.getId())
                    .setIssuedAt(markdownUserModel.getCreatedAt())
                    .signWith(authSigningKeyResolver.getSecretKey(), SignatureAlgorithm.HS512)
                    .compact();

        markdownUserModel.setJwtToken(jwtToken);
    }
}
