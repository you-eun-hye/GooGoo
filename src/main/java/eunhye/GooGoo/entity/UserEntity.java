package eunhye.GooGoo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eunhye.GooGoo.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userEmail;

    @Column(unique = true)
    private String userPassword;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BoardEntity> boards = new ArrayList<>();

    public static UserEntity toUserEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail(userDTO.getUserEmail());
        userEntity.setUserPassword(userDTO.getUserPassword());
        return userEntity;
    }

    public static UserEntity toEditUserEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setUserEmail(userDTO.getUserEmail());
        userEntity.setUserPassword(userDTO.getUserPassword());
        return userEntity;
    }
}
