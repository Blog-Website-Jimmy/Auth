package Auth.service;

import Auth.config.security.JwtService;
import Auth.exception.UserAlreadyExistsException;
import Auth.model.Auth;
import Auth.repository.UserRepository;
import Auth.request.AuthRequest;
import Auth.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsManager userDetailsManager;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public Auth register(RegisterRequest registerRequest) {
            var user = User.builder()
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .authorities("user")
                    .build();
        if (userDetailsManager.userExists(user.getUsername())) throw new UserAlreadyExistsException();

        userDetailsManager.createUser(user);
        var jwt = jwtService.generateToken(Map.of(user.getUsername(), user.getAuthorities()),user);
        return  Auth.builder().token(jwt).build();
    }

    public Auth login(AuthRequest authRequest) {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
            ));
        } catch (Exception e) {
            log.error("Auth failed user:{}",e.getMessage());
            throw new RuntimeException("Invalid credentials", e);
        }
        var user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow();
        var jwt = jwtService.generateToken(Map.of(user.getUsername(), user.getAuthorities()), user);
        user.setToken(jwt);
        userRepository.save(user);
        return  Auth.builder().token(jwt).build();
    }
}
