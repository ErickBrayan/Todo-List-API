package com.eb2.todolistapi.service;

import com.eb2.todolistapi.dto.ResponseJwtDTO;
import com.eb2.todolistapi.dto.SignInDTO;
import com.eb2.todolistapi.dto.SignUpDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {
    ResponseJwtDTO signUp(SignUpDTO signUpDTO);
    ResponseJwtDTO signIn(SignInDTO signInDTO);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
