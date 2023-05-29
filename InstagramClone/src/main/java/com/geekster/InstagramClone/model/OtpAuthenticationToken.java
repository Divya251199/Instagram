package com.geekster.InstagramClone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class OtpAuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpTokenId;

    private String email;

    private String otpToken;

    private LocalDateTime createdTime;

    public OtpAuthenticationToken(String email) {
        this.email = email;
        this.otpToken = UUID.randomUUID().toString();
        this.createdTime = LocalDateTime.now();
    }


}
