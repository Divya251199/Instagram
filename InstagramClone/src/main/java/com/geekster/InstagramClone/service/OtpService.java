package com.geekster.InstagramClone.service;

import com.geekster.InstagramClone.dto.OtpVerificationOutput;
import com.geekster.InstagramClone.model.AuthenticationToken;
import com.geekster.InstagramClone.model.Otp;
import com.geekster.InstagramClone.model.OtpAuthenticationToken;
import com.geekster.InstagramClone.model.User;
import com.geekster.InstagramClone.repository.IOtpRepository;
import com.geekster.InstagramClone.repository.IOtpTokenRepo;
import com.geekster.InstagramClone.repository.IUserRepo;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@Service
public class OtpService {

    @Autowired
    IOtpRepository otpRepository;

    @Autowired
    IUserRepo userRepo;


    @Autowired
    IOtpTokenRepo otpTokenRepo;

    public boolean sentOtp(String email) {
        User user = userRepo.findFirstByEmail(email);

        // check if user already present
        if(user != null)
        {
            throw new IllegalStateException("Instagram user already exists!!!!...sign in instead");
        }

        // generate OTP
        String otp = generateOTP();

        // encryption
        String encryptedOtp = null;

        try {
            encryptedOtp = encryptOtp(otp);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // save the otp in repository
        Otp otp1 = new Otp(email,encryptedOtp);
        otpRepository.save(otp1);


        // Send OTP via email
        sendEmail(email, "Your OTP for Instagram", "Your OTP is: " + otp);
        return true;
    }

    private String encryptOtp(String otp) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(otp.getBytes());
        byte[] digested = md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);

        return hash;

    }

    private static final String EMAIL_USERNAME = "divyahirve2511@gmail.com";
    private static final String EMAIL_PASSWORD = "fskkdbsauefujlnk";


    private String generateOTP() {
        // Generate a random 6-digit OTP
        int otpDigits = 6;
        StringBuilder otpBuilder = new StringBuilder();
        for (int i = 0; i < otpDigits; i++) {
            int digit = (int) (Math.random() * 10);
            otpBuilder.append(digit);
        }
        return otpBuilder.toString();
    }



    private void sendEmail(String toEmail, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("OTP sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public OtpVerificationOutput verifyOtp(String otp, String email) {
        Otp otp1 = otpRepository.findFirstByUserEmail(email);
        if ( otp1 == null ) {
            throw new IllegalStateException("Incorrect details...!!!");
        }

        String encryptedOtp = null;

        try {
            encryptedOtp = encryptOtp(otp);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        boolean isOtpValid = encryptedOtp.equals(otp1.getOtp());

        if(!isOtpValid)
        {
            throw new IllegalStateException("Otp invalid!!!!");
        }

        OtpAuthenticationToken token = new OtpAuthenticationToken(email);
        otpTokenRepo.save(token);
        return new OtpVerificationOutput("email verified successfully", token.getOtpToken());
    }
}
