package pl.bmiziura.app.user.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.bmiziura.app.construction.model.entity.UserAccountEntity;
import pl.bmiziura.app.user.domain.model.UserAccount;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserAccountEntityMapper {
    UserAccountEntity toUserAccountEntity(UserAccount user);

    void updateEntity(@MappingTarget UserAccountEntity entity, UserAccount user);
}
