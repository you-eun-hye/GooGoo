package eunhye.GooGoo.config.security;

import eunhye.GooGoo.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class SecurityDetails implements UserDetails, OAuth2User {

    // 일반 로그인
    private final UserEntity userEntity;

    // OAuth 로그인 (Google)
    private Map<String, Object> attributes;

    public SecurityDetails(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    public SecurityDetails(UserEntity userEntity, Map<String, Object> attributes){
        this.userEntity = userEntity;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return userEntity.getId()+"";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getAuthority().toString();
            }
        });
        return authorities;
    }

    @Override
    public String getPassword() {

        return userEntity.getUserPassword();
    }

    @Override
    public String getUsername() {

        return userEntity.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
