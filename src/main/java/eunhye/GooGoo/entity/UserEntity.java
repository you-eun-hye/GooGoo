package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.UserDTO;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(unique = true)
    private String userEmail;

    private String userPassword;

    @Enumerated(EnumType.STRING)
    private UserRole authority;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    public static UserEntity toUserEntity(UserDTO userDTO, PasswordEncoder passwordEncoder){
        UserEntity userEntity = new UserEntity();
        userEntity.userEmail = userDTO.getUserEmail();
        userEntity.userPassword = passwordEncoder.encode((userDTO.getUserPassword()));
        if(userDTO.getUserEmail().equals("ADMIN@gmail.com")) userEntity.authority = UserRole.ADMIN;
        else userEntity.authority = UserRole.USER;
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
