package pl.bmiziura.app.mail.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bmiziura.app.construction.model.MailTokenType;
import pl.bmiziura.app.mail.domain.service.MailTokenService;
import pl.bmiziura.app.mail.endpoint.mapper.MailTokenResponseMapper;
import pl.bmiziura.app.mail.endpoint.model.MailTokenResponse;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {
    private final MailTokenService mailTokenService;
    private final MailTokenResponseMapper responseMapper;

    @Operation(summary = "Creating and sending new token to user")
    @PostMapping("{email}")
    public ResponseEntity<MailTokenResponse> sendToken(@PathVariable String email, @RequestParam MailTokenType type) {
        var response = mailTokenService.createToken(email, type);

        return ResponseEntity.ok(responseMapper.toMailTokenResponse(response));
    }

    @Operation(summary = "Get Valid Mail Token")
    @GetMapping("/{email}/{token}")
    public ResponseEntity<MailTokenResponse> getToken(@PathVariable String email, @PathVariable String token, @RequestParam MailTokenType type) {
        var response = mailTokenService.getToken(email, token, type);

        return ResponseEntity.ok(responseMapper.toMailTokenResponse(response));
    }
}
