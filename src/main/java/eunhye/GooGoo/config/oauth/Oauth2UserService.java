package eunhye.GooGoo.config.oauth;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import eunhye.GooGoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 구글로부터 받은 userRequest 데이터에 대해 후처리하는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oauth2User = super.loadUser(userRequest);

        // 회원가입 강제 진행
        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oauth2User.getAttribute("sub");
        String userNickname = provider+"_"+providerId;
        String userEmail = oauth2User.getAttribute("email");
        String userPassword = passwordEncoder.encode("겟인데어");
        UserRole authority = UserRole.USER;

        UserEntity userEntity = userRepository.findByUserEmail(userEmail);

        if(userEntity == null){
            userEntity = UserEntity.builder()
                    .userNickname(userNickname)
                    .userEmail(userEmail)
                    .userPassword(userPassword)
                    .authority(authority)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }

        return new SecurityDetails(userEntity, oauth2User.getAttributes());
    }
}
