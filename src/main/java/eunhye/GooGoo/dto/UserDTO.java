package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String userNickname;
    private String userEmail;
    private String userPassword;
    private LocalDateTime userCreatedTime;
    private UserRole authority;

    // google 관련
    private String provider;
    private String providerId;

    // 회원 생성
    public static UserDTO toUserDTO(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUserNickname(userEntity.getUserNickname());
        userDTO.setUserEmail(userEntity.getUserEmail());
        userDTO.setUserPassword(userEntity.getUserPassword());
        userDTO.setUserCreatedTime(userEntity.getCreatedTime());
        userDTO.setAuthority(UserRole.USER);
        userDTO.setProvider(userEntity.getProvider());
        userDTO.setProviderId(userEntity.getProviderId());
        return userDTO;
    }

    // 관리자 생성
    public static UserDTO toAdminDTO(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUserNickname(userEntity.getUserNickname());
        userDTO.setUserEmail(userEntity.getUserEmail());
        userDTO.setUserPassword(userEntity.getUserPassword());
        userDTO.setUserCreatedTime(userEntity.getCreatedTime());
        userDTO.setAuthority(UserRole.ADMIN);
        userDTO.setProvider(userEntity.getProvider());
        userDTO.setProviderId(userEntity.getProviderId());
        return userDTO;
    }
}