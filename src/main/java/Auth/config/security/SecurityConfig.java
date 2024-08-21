package Auth.config.security;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public Flyway executeMigrations() {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5434/users", "postgres", "123456")
                .load();

        flyway.migrate();
        return flyway;
    }
    @Bean
    public UserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public ApplicationRunner createAdmin(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userDetailsManager.userExists("admin")) {
                userDetailsManager.createUser(User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .authorities("admin")
                        .build());
            }
        };
    }

}
