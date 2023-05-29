package com.geekster.InstagramClone.controller;

import com.geekster.InstagramClone.dto.OtpVerificationOutput;
import com.geekster.InstagramClone.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    OtpService otpService;

    @PostMapping("/emailOtp")
    public ResponseEntity<String> sendOtp(@RequestBody String email) {
        HttpStatus status;
        String message = null;
        if (otpService.sentOtp(email)) {
            status = HttpStatus.OK;
            message = "Otp sent successfully";
        }
        else {
            status = HttpStatus.BAD_REQUEST;
            message = "Enter correct email address";
        }
        return new ResponseEntity<>(message, status);
    }


    @PostMapping("/emailsignup")
    public OtpVerificationOutput verifyOtp(@RequestBody String otp, @RequestParam String email) {
        return otpService.verifyOtp(otp, email);
    }
}
