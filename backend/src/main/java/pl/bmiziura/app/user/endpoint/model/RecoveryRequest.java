package pl.bmiziura.app.user.endpoint.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryRequest {
    private String email;
    private String token;
    private String password;
    private boolean logout = true;
}
