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

    public void saveConfirmationToken(User user) {
        ConfirmationToken token = generateConfirmationToken(user);
        confirmationTokenRepository.save(token);
    }

    private ConfirmationToken generateConfirmationToken(User user) {
        ConfirmationToken token = ConfirmationToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .createdTime(Instant.now())
                .expiryTime(Instant.now().plus(15, ChronoUnit.MINUTES))
                .build();
        return token;
    }
}
