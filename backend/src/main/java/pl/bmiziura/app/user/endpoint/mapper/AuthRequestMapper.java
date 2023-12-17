package pl.bmiziura.app.user.endpoint.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.bmiziura.app.user.endpoint.model.UserLoginRequest;
import pl.bmiziura.app.user.endpoint.model.UserRegisterRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AuthRequestMapper {
    UserLoginRequest toUserLoginRequest(UserRegisterRequest request);
}
