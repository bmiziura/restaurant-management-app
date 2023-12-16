package pl.bmiziura.app.user.endpoint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bmiziura.app.user.domain.model.UserRole;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<UserRole> roles;
}
