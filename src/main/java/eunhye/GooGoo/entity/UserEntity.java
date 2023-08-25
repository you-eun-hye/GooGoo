package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.CommentDTO;
import eunhye.GooGoo.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userNickname;

    @Column(unique = true)
    private String userEmail;

    private String userPassword;

    @Enumerated(EnumType.STRING)
    private UserRole authority;

//    // google 관련
    private String provider;
    private String providerId;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PaymentEntity> paymentEntityList = new ArrayList<>();

    // 회원 생성
    public static UserEntity toUserEntity(UserDTO userDTO, PasswordEncoder passwordEncoder){
        UserEntity userEntity = new UserEntity();
        userEntity.userNickname = userDTO.getUserNickname();
        userEntity.userEmail = userDTO.getUserEmail();
        userEntity.userPassword = passwordEncoder.encode((userDTO.getUserPassword()));
        userEntity.authority = UserRole.USER;
        userEntity.provider = userDTO.getProvider();
        userEntity.providerId = userDTO.getProviderId();
        return userEntity;
    }

    // 관리자 생성
    public static UserEntity toAdminEntity(UserDTO userDTO, PasswordEncoder passwordEncoder){
        UserEntity userEntity = new UserEntity();
        userEntity.userNickname = userDTO.getUserNickname();
        userEntity.userEmail = userDTO.getUserEmail();
        userEntity.userPassword = passwordEncoder.encode((userDTO.getUserPassword()));
        userEntity.authority = UserRole.ADMIN;
        userEntity.provider = userDTO.getProvider();
        userEntity.providerId = userDTO.getProviderId();
        return userEntity;
    }

    // 유저 정보 수정
    public static UserEntity toEditUserEntity(UserDTO userDTO, PasswordEncoder passwordEncoder){
        UserEntity userEntity = new UserEntity();
        userEntity.id = userDTO.getId();
        userEntity.userNickname = userDTO.getUserNickname();
        userEntity.userEmail = userDTO.getUserEmail();
        userEntity.userPassword = passwordEncoder.encode((userDTO.getUserPassword()));
        userEntity.authority = userDTO.getAuthority();
        userEntity.provider = userDTO.getProvider();
        userEntity.providerId = userDTO.getProviderId();
        return userEntity;
    }
}
