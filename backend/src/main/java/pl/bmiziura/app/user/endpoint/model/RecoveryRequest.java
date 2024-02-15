package pl.bmiziura.app.user.endpoint.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryRequest {
    private String email;
    private String token;
    private String password;
    private boolean logout = true;
}
