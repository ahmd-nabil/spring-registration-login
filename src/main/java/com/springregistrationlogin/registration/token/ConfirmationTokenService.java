package com.springregistrationlogin.registration.token;

import com.springregistrationlogin.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken findByToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(() ->new RuntimeException("Token Not Found"));
    }

    public ConfirmationToken saveConfirmationToken(User user) {
        ConfirmationToken confirmationToken = generateConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        return confirmationToken;
    }

    private ConfirmationToken generateConfirmationToken(User user) {
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .createdTime(Instant.now())
                .expiryTime(Instant.now().plus(15, ChronoUnit.MINUTES))
                .build();
        return confirmationToken;
    }
}
