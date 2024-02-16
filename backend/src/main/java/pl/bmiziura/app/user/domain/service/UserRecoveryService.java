package pl.bmiziura.app.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.bmiziura.app.construction.model.MailTokenType;
import pl.bmiziura.app.mail.domain.service.MailTokenService;
import pl.bmiziura.app.user.endpoint.model.RecoveryRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRecoveryService {
    private final UserService userService;
    private final MailTokenService tokenService;

    public void changePassword(RecoveryRequest request) {
        System.out.println(request);
        if(tokenService.useToken(request.getEmail(), request.getToken(), MailTokenType.PASSWORD_CHANGE)) {
            var user = userService.getAccountEntity(request.getEmail());

            userService.changePassword(user, request.getPassword());

            if(request.isLogout()) {
                // todo remove all refresh tokens (after adding database for refresh tokens)
            }
        }
    }
}
