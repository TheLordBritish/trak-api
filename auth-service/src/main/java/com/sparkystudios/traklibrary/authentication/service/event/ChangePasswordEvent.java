package com.sparkystudios.traklibrary.authentication.service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordEvent {

    private String username;

    private String emailAddress;

    private String recoveryToken;
}