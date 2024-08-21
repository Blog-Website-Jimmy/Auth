package Auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Builder
@Getter
public class Auth {
    private String token;
}
