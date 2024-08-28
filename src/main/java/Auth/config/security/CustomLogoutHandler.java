package Auth.config.security;

import Auth.entity.UserEntity;
import Auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void logout( HttpServletRequest request,  HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
        userEntity.setToken("");
        userRepository.save(userEntity);
        response.setStatus(HttpServletResponse.SC_FOUND);
    }
}
