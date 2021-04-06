package com.springregistrationlogin.user;

import com.springregistrationlogin.registration.token.ConfirmationToken;
import com.springregistrationlogin.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Transactional
    public ConfirmationToken signup(User user) {
        User oldUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(oldUser != null) {
            if(oldUser.isEnabled())
                throw new RuntimeException("Email already exists");
            user.setId(oldUser.getId());
            /**
             *  TODO Make User_Token Bi-Directional to check if oldUser confirmation token is expired then send new token, otherwise DON'T
            **/
        }
        userRepository.save(user);
        return confirmationTokenService.saveConfirmationToken(user);
    }

    public void enableUser(User user) {
        user.setEnabled(true);
    }
}
