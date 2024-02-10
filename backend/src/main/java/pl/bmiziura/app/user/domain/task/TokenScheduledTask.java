package pl.bmiziura.app.user.domain.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.bmiziura.app.construction.model.MailTokenStatus;
import pl.bmiziura.app.construction.model.repository.MailTokenRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenScheduledTask {
    private final MailTokenRepository mailTokenRepository;

    @Scheduled(cron = "0 0/5 * * * *")
    public void expireTokens() {
        log.info("Rozpoczynam usuwanie nieważnych tokenów");

        var expiredTokens = mailTokenRepository.findAllByTokenExpired();
        expiredTokens.forEach(token -> token.setStatus(MailTokenStatus.EXPIRED));
        mailTokenRepository.saveAll(expiredTokens);

        log.info("Usunięto {} nieważnych tokenów", expiredTokens.size());
    }
}
