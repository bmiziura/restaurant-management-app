package pl.bmiziura.app.mail.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.bmiziura.app.construction.model.entity.MailTokenEntity;
import pl.bmiziura.app.mail.domain.model.MailToken;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MailTokenMapper {
    MailToken toMailToken(MailTokenEntity entity);
}
