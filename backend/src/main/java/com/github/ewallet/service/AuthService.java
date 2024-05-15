package com.github.ewallet.service;

import com.github.ewallet.dto.mapper.SignupRequestMapper;
import com.github.ewallet.dto.request.LoginRequest;
import com.github.ewallet.dto.request.SignupRequest;
import com.github.ewallet.dto.response.CommandResponse;
import com.github.ewallet.dto.response.JwtResponse;
import com.github.ewallet.exception.ElementAlreadyExistsException;
import com.github.ewallet.model.User;
import com.github.ewallet.repository.UserRepository;
import com.github.ewallet.security.JwtUtils;
import com.github.ewallet.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.ewallet.common.Constants.*;

/**
 * Service used for Authentication related operations
 */
@Slf4j(topic = "AuthService")
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final SignupRequestMapper signupRequestMapper;

    /**
     * Authenticates users by their credentials
     *
     * @param request
     * @return JwtResponse
     */
    public JwtResponse login(LoginRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword().trim()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toList();

        log.info(LOGGED_IN_USER, new Object[]{request.getUsername()});
        return JwtResponse
                .builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .roles(roles).build();
    }

    /**
     * Registers a user by provided credentials and user info
     *
     * @param request
     * @return id of the registered user
     */
    public CommandResponse signup(SignupRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername().trim()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER_NAME);
        if (userRepository.existsByEmailIgnoreCase(request.getEmail().trim()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER_EMAIL);

        final User user = signupRequestMapper.toEntity(request);
        userRepository.save(user);
        log.info(CREATED_USER, new Object[]{user.getUsername()});
        return CommandResponse.builder().id(user.getId()).build();
    }
}
