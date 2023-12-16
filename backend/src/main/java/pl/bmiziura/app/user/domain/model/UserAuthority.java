package pl.bmiziura.app.user.domain.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
