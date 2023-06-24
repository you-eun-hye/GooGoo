package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.UserDTO;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @Column
    private int boardAttached;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    public static UserEntity toUserEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.userEmail = userDTO.getUserEmail();
        userEntity.userPassword = userDTO.getUserPassword();
        userEntity.boardAttached = 0;
        return userEntity;
    }

    public static UserEntity toEditUserEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.id = userDTO.getId();
        userEntity.userEmail = userDTO.getUserEmail();
        userEntity.userPassword = userDTO.getUserPassword();
        userEntity.boardAttached = userDTO.get
        return userEntity;
    }
}
