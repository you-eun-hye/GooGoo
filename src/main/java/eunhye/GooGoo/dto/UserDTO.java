package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import lombok.*;

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
    private UserRole authority;

    // google 관련
    private String provider;
    private String providerId;

    public static UserDTO toUserDTO(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUserNickname(userEntity.getUserNickname());
        userDTO.setUserEmail(userEntity.getUserEmail());
        userDTO.setUserPassword(userEntity.getUserPassword());
        userDTO.setAuthority(UserRole.USER);
        userDTO.setProvider(userEntity.getProvider());
        userDTO.setProviderId(userEntity.getProviderId());
        return userDTO;
    }
}