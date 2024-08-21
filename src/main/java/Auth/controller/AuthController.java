package Auth.controller;

import Auth.model.Auth;
import Auth.request.AuthRequest;
import Auth.request.RegisterRequest;
import Auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
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
    public ResponseEntity<Auth> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok().body(authService.login(authRequest));
    }
}
