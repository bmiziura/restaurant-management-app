package pl.bmiziura.app.mail.endpoint.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.bmiziura.app.mail.domain.model.MailToken;
import pl.bmiziura.app.mail.endpoint.model.MailTokenResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MailTokenResponseMapper {
    MailTokenResponse toMailTokenResponse(MailToken token);
}
