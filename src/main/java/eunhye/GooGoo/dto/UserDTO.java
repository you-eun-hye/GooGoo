package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String userEmail;
    private String userPassword;
    private UserRole role;

    public static UserDTO toUserDTO(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUserEmail(userEntity.getUserEmail());
        userDTO.setUserPassword(userEntity.getUserPassword());
        userDTO.setRole(UserRole.USER);
        return userDTO;
    }
}