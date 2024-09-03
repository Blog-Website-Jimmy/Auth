package Auth.controller;

import Auth.model.Auth;
import Auth.request.AuthRequest;
import Auth.request.RegisterRequest;
import Auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("register")
    public ResponseEntity<Auth> registerUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok().body(authService.register(registerRequest));
    }

    @PostMapping("login")
    public ResponseEntity<Auth> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        response.reset();
        return ResponseEntity.ok().body(authService.login(authRequest));
    }
}
