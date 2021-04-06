package com.springregistrationlogin.registration;

import com.springregistrationlogin.email.EmailBuilder;
import com.springregistrationlogin.email.EmailSender;
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
    private final EmailSender emailSender;
    private final EmailBuilder emailBuilder;

    public String register(RegistrationRequest request) {
        boolean validEmail = emailValidator.test(request.getEmail());
        if(!validEmail) {
            throw new RuntimeException("Email is not valid");
        }

        User user = User.builder()
                            .firstname(request.getFirstname())
                            .lastname(request.getLastname())
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .userRole(UserRole.USER)
                            .nonExpired(true).nonLocked(true).credentialNonExpired(true).enabled(false).build();
        ConfirmationToken confirmationToken = userService.signup(user);
        String activationLink = "http://localhost:8080/api/registration?token="+confirmationToken.getToken();
        emailSender.Send(user.getEmail(), emailBuilder.build(user.getFirstname(), activationLink));
        return "Check your email address to activate your Account";
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
