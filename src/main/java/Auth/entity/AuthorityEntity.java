package Auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@IdClass(AuthorityId.class)
@Table(name = "authorities")
public class AuthorityEntity {
    @Id
    private String username;
    @Id
    private String authority;
}
