package com.lab.test.task.monitorsensors.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDto {

    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
