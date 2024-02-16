package pl.bmiziura.app.user.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.bmiziura.app.construction.model.entity.RefreshTokenEntity;
import pl.bmiziura.app.construction.model.entity.UserAccountEntity;
import pl.bmiziura.app.user.domain.model.RefreshToken;
import pl.bmiziura.app.user.domain.model.UserAccount;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, imports = UserAccountMapper.class)
public interface RefreshTokenMapper {
    RefreshToken toRefreshToken(RefreshTokenEntity entity);
}
