package pl.bmiziura.app.user.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bmiziura.app.user.domain.service.UserRecoveryService;
import pl.bmiziura.app.user.endpoint.model.RecoveryRequest;

@RestController
@RequestMapping("/api/recovery")
@RequiredArgsConstructor
public class RecoveryController {
    private final UserRecoveryService userRecoveryService;

    @Operation(summary = "Change user password via recovery")
    @PostMapping("/changePassword")
    public ResponseEntity<Void> changePassword(RecoveryRequest request) {
        userRecoveryService.changePassword(request);

        return ResponseEntity.ok().build();
    }
}
