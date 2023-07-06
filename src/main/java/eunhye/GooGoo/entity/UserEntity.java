package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "userEmail")
    private String userEmail;

    @Column(unique = true, name = "userPassword")
    private String userPassword;

    @Column(name = "userRole")
    @Enumerated(EnumType.STRING)
    private Role authority;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    public static UserEntity toUserEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.userEmail = userDTO.getUserEmail();
        userEntity.userPassword = userDTO.getUserPassword();
        userEntity.authority = Role.USER;
        return userEntity;
    }

    public static UserEntity toEditUserEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.id = userDTO.getId();
        userEntity.userEmail = userDTO.getUserEmail();
        userEntity.userPassword = userDTO.getUserPassword();
        return userEntity;
    }
}
