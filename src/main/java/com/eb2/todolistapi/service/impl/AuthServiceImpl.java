package com.eb2.todolistapi.service.impl;

import com.eb2.todolistapi.dto.ResponseJwtDTO;
import com.eb2.todolistapi.dto.SignInDTO;
import com.eb2.todolistapi.dto.SignUpDTO;
import com.eb2.todolistapi.entities.User;
import com.eb2.todolistapi.exception.DuplicateEmailException;
import com.eb2.todolistapi.repositories.UserRepository;
import com.eb2.todolistapi.security.jwt.JwtService;
import com.eb2.todolistapi.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public ResponseJwtDTO signUp(SignUpDTO signUpDTO) {

        Optional<User> userExist = userRepository.findByEmail(signUpDTO.email());

        if (userExist.isPresent()) {
            throw new DuplicateEmailException("Email already exist", "400", HttpStatus.BAD_REQUEST);
        } else {
            User user = new User();
            user.setName(signUpDTO.name());
            user.setEmail(signUpDTO.email());
            user.setPassword(passwordEncoder.encode(signUpDTO.password()));

            userRepository.save(user);

            String token = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            return new ResponseJwtDTO(token, refreshToken);
        }
    }

    @Override
    public ResponseJwtDTO signIn(SignInDTO signInDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDTO.email(),
                        signInDTO.password()
                )
        );

        User user = userRepository.findByEmail(signInDTO.email()).orElseThrow();

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new ResponseJwtDTO(token, refreshToken);
    }

    @Override
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);

        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            User user = this.userRepository.findByEmail(username).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                String token = jwtService.generateToken(user);
                var authResponse = new ResponseJwtDTO(token, refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

    }
}
