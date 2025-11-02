package com.hotelbooking.controller;

import com.hotelbooking.dto.request.LoginRequest;
import com.hotelbooking.dto.request.RegisterRequest;
import com.hotelbooking.dto.response.ApiResponse;
import com.hotelbooking.dto.response.AuthResponse;
import com.hotelbooking.dto.response.UserResponse;
import com.hotelbooking.model.User;
import com.hotelbooking.service.JwtService;
import com.hotelbooking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse("Email is already taken!", false));
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());

        /*if (request.getEmail().contains("admin")) {
            user.setRole(User.UserRole.ADMIN);
        }*/

        User savedUser = userService.saveUser(user);
        String jwtToken = jwtService.generateToken(savedUser);

        return ResponseEntity.ok(new AuthResponse(jwtToken, savedUser));
    }

    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse("Email is already taken!", false));
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setRole(User.UserRole.ADMIN);

        User savedUser = userService.saveAdmin(user);
        String jwtToken = jwtService.generateToken(savedUser);

        return ResponseEntity.ok(new AuthResponse(jwtToken, savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(user);

            return ResponseEntity.ok(new AuthResponse(jwtToken, user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid email or password", false));
        }

    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated() ||
                    authentication.getPrincipal().equals("anonymousUser")) {
                return ResponseEntity.status(401).body(new ApiResponse("Unauthorized", false));
            }

            User user = (User) authentication.getPrincipal();
            UserResponse userResponse = new UserResponse(user);

            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Error retrieving user data: "
            + e.getMessage(), false));
        }
    }
}
