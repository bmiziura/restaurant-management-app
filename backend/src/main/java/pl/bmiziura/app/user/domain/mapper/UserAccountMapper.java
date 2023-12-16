package pl.bmiziura.app.user.domain.mapper;

import org.mapstruct.*;
import pl.bmiziura.app.construction.model.entity.UserAccountEntity;
import pl.bmiziura.app.user.domain.model.UserAccount;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserAccountMapper {
    UserAccount toUserAccount(UserAccountEntity entity);
}
