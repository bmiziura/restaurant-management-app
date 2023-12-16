package pl.bmiziura.app.user.endpoint.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {
    private String jwtToken;
}
