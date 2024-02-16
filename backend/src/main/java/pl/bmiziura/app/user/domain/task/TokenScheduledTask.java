package pl.bmiziura.app.user.domain.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.bmiziura.app.construction.model.MailTokenStatus;
import pl.bmiziura.app.construction.model.RefreshTokenStatus;
import pl.bmiziura.app.construction.model.repository.MailTokenRepository;
import pl.bmiziura.app.construction.model.repository.RefreshTokenRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenScheduledTask {
    private final MailTokenRepository mailTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "0 0/5 * * * *")
    public void expireTokens() {
        log.info("Rozpoczynam usuwanie nieważnych tokenów");

        var expiredTokens = 0;

        expiredTokens += expireMailTokens();
        expiredTokens += expireRefreshTokens();

        log.info("Usunięto {} nieważnych tokenów", expiredTokens);
    }

    private int expireMailTokens() {
        var expiredTokens = mailTokenRepository.findAllByTokenExpired();
        expiredTokens.forEach(token -> token.setStatus(MailTokenStatus.EXPIRED));
        mailTokenRepository.saveAll(expiredTokens);

        return expiredTokens.size();
    }

    private int expireRefreshTokens() {
        var expiredTokens = refreshTokenRepository.findAllByTokenExpired();
        expiredTokens.forEach(token -> token.setStatus(RefreshTokenStatus.EXPIRED));
        refreshTokenRepository.saveAll(expiredTokens);

        return expiredTokens.size();
    }
}
