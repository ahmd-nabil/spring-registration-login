package com.springregistrationlogin.registration;

import com.springregistrationlogin.user.User;
import com.springregistrationlogin.user.UserRole;
import com.springregistrationlogin.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final PasswordEncoder passwordEncoder;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(isValidEmail) {
            User user = User.builder()
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .userRole(UserRole.ADMIN)
                                .nonExpired(true).nonLocked(true).credentialNonExpired(true).enabled(false).build();
            userService.signup(user);
            return "Voila";
        }
        throw new RuntimeException("Email is not valid");
    }
}
