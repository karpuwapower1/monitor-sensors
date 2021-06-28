package com.lab.test.task.monitorsensors.controller;

import com.lab.test.task.monitorsensors.dto.LoginDto;
import com.lab.test.task.monitorsensors.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto request) {
        val authentication = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
        authenticationManager.authenticate(authentication);
        val token = tokenProvider.generate(userDetailsService.loadUserByUsername(request.getLogin()));
        return ResponseEntity.ok(token);
    }
}
