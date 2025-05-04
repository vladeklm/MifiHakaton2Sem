package it.globus.finaudit.controller.user;


import it.globus.finaudit.DTO.AuthenticationDTO;
import it.globus.finaudit.entity.User;
import it.globus.finaudit.security.UserDetailsImpl;
import it.globus.finaudit.service.user.UserService;
import it.globus.finaudit.util.JwtUtils;
import it.globus.finaudit.util.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                          UserService userService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthenticationDTO AuthenticationDTO) {
        User user = userMapper.toUser(AuthenticationDTO);
        userService.registerUser(user);
        return ResponseEntity.ok("Пользователь сохранен");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationDTO authenticationDTO) {
        try {
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(),
                            authenticationDTO.getPassword()));
            var userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateJwtToken(userDetails);
            return ResponseEntity.ok().body(Map.of("jwt-token", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Incorrect credentials!"));
        }
    }
}


