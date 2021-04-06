package com.springregistrationlogin.registration.token;

import com.springregistrationlogin.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant createdTime;

    @Column(nullable = false)
    private Instant expiryTime;

    private Instant confirmationTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
