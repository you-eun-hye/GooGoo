package eunhye.GooGoo.config.security;

import eunhye.GooGoo.entity.UserEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private UserEntity userEntity;

    public SecurityUser(UserEntity userEntity) {
        super(userEntity.getId().toString(), userEntity.getUserPassword(),
                AuthorityUtils.createAuthorityList(userEntity.getAuthority().toString()));
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}
