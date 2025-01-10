package com.eb2.todolistapi.controller;

import com.eb2.todolistapi.dto.ResponseJwtDTO;
import com.eb2.todolistapi.dto.SignInDTO;
import com.eb2.todolistapi.dto.SignUpDTO;
import com.eb2.todolistapi.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseJwtDTO> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {

        return ResponseEntity.ok(authService.signUp(signUpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseJwtDTO> signIn(@Valid @RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(authService.signIn(signInDTO));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }


}
