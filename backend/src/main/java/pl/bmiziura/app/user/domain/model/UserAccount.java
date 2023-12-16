package pl.bmiziura.app.user.domain.model;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<UserRole> roles;
}
