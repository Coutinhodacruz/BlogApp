package com.example.blog.service;

import com.example.blog.exception.UserNotFoundException;
import com.example.blog.model.Token;
import com.example.blog.repositories.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@AllArgsConstructor
public class BlogTokenService implements TokenService{


    private final TokenRepository tokenRepository;


    @Override
    public String createToken(String email) {
        String token = generateToken();
        Token userToken = new Token();
        userToken.setToken(token);
        userToken.setOwnerEmail(email.toLowerCase());
        Token savedToken = tokenRepository.save(userToken);

        return savedToken.getToken();
    }



    @Override
    public Token findByOwnerEmail(String email) {
        return tokenRepository.findByOwnerEmail(email.toLowerCase())
                .orElseThrow(()-> new UserNotFoundException("The provided email is not attached to the token"));
    }

    @Override
    public void deleteToken(Long id) {

        tokenRepository.deleteById(id);
    }

    private String generateToken() {
        StringBuilder otp = new StringBuilder();
        for (int index = 0; index < 5; index++) {
            Random random = new Random();
            int digit = random.nextInt(1,9);
            otp.append(digit);
        }
        return String.valueOf(otp);
    }
}
