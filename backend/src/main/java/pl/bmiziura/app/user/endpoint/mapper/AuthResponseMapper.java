package pl.bmiziura.app.user.endpoint.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.bmiziura.app.user.domain.model.UserAccount;
import pl.bmiziura.app.user.endpoint.model.UserLoginResponse;
import pl.bmiziura.app.user.endpoint.model.UserRegisterResponse;
import pl.bmiziura.app.user.endpoint.model.AuthUserResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AuthResponseMapper {
    UserRegisterResponse toUserRegisterResponse(UserLoginResponse response);

    AuthUserResponse toUserResponse(UserAccount user);
}
