package Auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends TimeIntegration implements UserDetails {

    @Id
    private String username;
    private String name;
    @Column(nullable = false)
    private String password;
    public boolean enabled = true;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private List<AuthorityEntity> authorities = new ArrayList<>();

    private String token;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream().map(el -> new SimpleGrantedAuthority(el.getAuthority())).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
