package com.geekster.InstagramClone.service;

import com.geekster.InstagramClone.model.OtpAuthenticationToken;
import com.geekster.InstagramClone.model.User;
import com.geekster.InstagramClone.repository.IOtpTokenRepo;
import com.geekster.InstagramClone.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpAuthenticationService {

    @Autowired
    IOtpTokenRepo otpTokenRepo;


    public boolean authenticateToken(User signUpDto, String token) {
        OtpAuthenticationToken token1 = otpTokenRepo.findByOtpToken(token);
        if ( token1 != null ) {
            if(token1.getEmail().equals(signUpDto.getEmail())){
                return true;
            }
        }
        return false;
    }
}
