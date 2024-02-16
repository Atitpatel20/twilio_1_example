package com.twilio1.example.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio1.example.payload.SmsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    @PostMapping("/send-sms")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest smsRequest) {
        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(smsRequest.getTo()),
                            new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                            smsRequest.getMessage())
                    .create();
            return ResponseEntity.ok("SMS sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send SMS: " + e.getMessage());
        }
    }
}
