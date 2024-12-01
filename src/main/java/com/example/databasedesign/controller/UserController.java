package com.example.databasedesign.controller;

import com.example.databasedesign.dto.request.SignInRequest;
import com.example.databasedesign.dto.request.SignUpRequest;
import com.example.databasedesign.dto.response.CheckUserIdResponse;
import com.example.databasedesign.dto.response.SignInResponse;
import com.example.databasedesign.service.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final LogInService logInService;

    @GetMapping("/check-id")
    public ResponseEntity<CheckUserIdResponse> checkUserIdDuplicate(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(logInService.checkUserId(userId));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        logInService.signUp(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(logInService.signIn(signInRequest));
    }
}
