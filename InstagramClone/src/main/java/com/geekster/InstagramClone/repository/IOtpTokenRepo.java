package com.geekster.InstagramClone.repository;

import com.geekster.InstagramClone.model.OtpAuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOtpTokenRepo extends JpaRepository<OtpAuthenticationToken, Long> {
    OtpAuthenticationToken findByOtpToken(String token);
}
