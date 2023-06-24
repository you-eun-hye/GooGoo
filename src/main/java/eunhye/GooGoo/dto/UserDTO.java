package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String userEmail;
    private String userPassword;
    private int boardAttached;
    private List<BoardEntity> boardList = new ArrayList<>();

    public static UserDTO toUserDTO(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUserEmail(userEntity.getUserEmail());
        userDTO.setUserPassword(userEntity.getUserPassword());

        if(userEntity.getBoardAttached() == 0){
            userDTO.setBoardAttached(userEntity.getBoardAttached());
        }else{
            userDTO.setBoardAttached(userEntity.getBoardAttached());

        }
        return userDTO;
    }
}
