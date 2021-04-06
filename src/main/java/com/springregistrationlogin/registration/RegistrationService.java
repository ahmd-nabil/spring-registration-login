package com.springregistrationlogin.registration;

import com.springregistrationlogin.registration.token.ConfirmationToken;
import com.springregistrationlogin.registration.token.ConfirmationTokenService;
import com.springregistrationlogin.user.User;
import com.springregistrationlogin.user.UserRole;
import com.springregistrationlogin.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(isValidEmail) {
            User user = User.builder()
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .userRole(UserRole.USER)
                                .nonExpired(true).nonLocked(true).credentialNonExpired(true).enabled(false).build();
            userService.signup(user);
            return "Voila";
        }
        throw new RuntimeException("Email is not valid");
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
        if(confirmationToken.getExpiryTime().isBefore(Instant.now())) {
            throw new RuntimeException("Token has Expired");
        }

        if(confirmationToken.getConfirmationTime() != null) {
            throw new RuntimeException("Email already Confirmed");
        }

        confirmationToken.setConfirmationTime(Instant.now());
        userService.enableUser(confirmationToken.getUser());
        return "Confirmed Token & Enabled User";
    }
}
