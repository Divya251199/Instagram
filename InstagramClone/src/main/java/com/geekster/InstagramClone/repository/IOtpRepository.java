package com.geekster.InstagramClone.repository;

import com.geekster.InstagramClone.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOtpRepository extends JpaRepository<Otp, Long> {


    Otp findFirstByUserEmail(String email);
}
