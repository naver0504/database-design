package com.example.databasedesign.service;

import com.example.databasedesign.common.error.CustomHttpException;
import com.example.databasedesign.dto.request.SignInRequest;
import com.example.databasedesign.dto.request.SignUpRequest;
import com.example.databasedesign.dto.response.CheckUserIdResponse;
import com.example.databasedesign.dto.response.SignInResponse;
import com.example.databasedesign.entity.User;
import com.example.databasedesign.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.databasedesign.common.error.HttpExceptionMessage.NOT_VALID_PASSWORD;
import static com.example.databasedesign.common.error.HttpExceptionMessage.NOT_VALID_USER_ID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LogInService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CheckUserIdResponse checkUserId(String userId) {
        return new CheckUserIdResponse(userRepository.existsByUserId(userId));
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        userRepository.findById(signUpRequest.userId())
                .ifPresentOrElse(
                        user -> {
                            throw new CustomHttpException(NOT_VALID_USER_ID);
                        },
                        () -> {
                            User user = User.builder()
                                    .id(signUpRequest.userId())
                                    .password(passwordEncoder.encode(signUpRequest.password()))
                                    .nickname(signUpRequest.nickname())
                                    .build();
                            userRepository.save(user);
                        }
                );
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        String userId = signInRequest.userId();
        String password = signInRequest.password();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomHttpException(NOT_VALID_USER_ID));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomHttpException(NOT_VALID_PASSWORD);
        }

        return new SignInResponse(userId);
    }
}
