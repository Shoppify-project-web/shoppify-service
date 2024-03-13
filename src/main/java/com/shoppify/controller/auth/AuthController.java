package com.shoppify.controller.auth;

import com.shoppify.dto.payload.request.LoginRequest;
import com.shoppify.dto.payload.request.RegisterRequest;
import com.shoppify.dto.payload.response.AuthenticationResponse;
import com.shoppify.service.impl.AuthenticationService;
import com.shoppify.service.impl.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> getAccessToken(@RequestBody LoginRequest requestDto){
        return ResponseEntity.ok(authenticationService.loginAuthenticate(requestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody RegisterRequest requestDto){
        if (authenticationService.register(requestDto)){
            return ResponseEntity.status(HttpStatus.CREATED).body("Create user successfully!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already exist, please choice another one");
    }
}