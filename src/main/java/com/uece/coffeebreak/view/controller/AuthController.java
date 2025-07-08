package com.uece.coffeebreak.view.controller;

import com.uece.coffeebreak.config.JwtUtil;
import com.uece.coffeebreak.entity.User;
import com.uece.coffeebreak.entity.enums.Role;
import com.uece.coffeebreak.service.UserService;
import com.uece.coffeebreak.shared.UserDTO;
import com.uece.coffeebreak.view.model.request.LoginRequest;
import com.uece.coffeebreak.view.model.request.UserRequest;
import com.uece.coffeebreak.view.model.response.AuthResponse;
import com.uece.coffeebreak.view.model.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDTO userDTO = service.findByEmail(request.getEmail());
        User user = new ModelMapper().map(userDTO, User.class);
        String token = jwtUtil.generateToken(user);
        AuthResponse response = new AuthResponse(
                token,
                user.getEmail(),
                user.getPhone(),
                user.getCountry(),
                user.getRole(),
                user.getId(),
                user.getName()
        );
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest userRequest) {
        if (service.hasEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        UserDTO userDTO = new ModelMapper().map(userRequest, UserDTO.class);
        userDTO.setRole(Role.CLIENT);
        userDTO = service.insert(userDTO);
        User user = new ModelMapper().map(userDTO, User.class);
        String token = jwtUtil.generateToken(user);
        AuthResponse response = new AuthResponse(
                token,
                user.getEmail(),
                user.getPhone(),
                user.getCountry(),
                user.getRole(),
                user.getId(),
                user.getName()
        );
        URI uri = ServletUriComponentsBuilder.fromPath("http://localhost:8080").path("/users/{id}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}

