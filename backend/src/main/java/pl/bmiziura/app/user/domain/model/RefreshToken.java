package pl.bmiziura.app.user.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class RefreshToken {
    private Long id;
    private String token;
    private LocalDateTime expireTime;
    private UserAccount user;
}
