package com.sparkystudios.traklibrary.email.service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationEvent {

    private String username;

    private String emailAddress;

    private String verificationCode;
}
