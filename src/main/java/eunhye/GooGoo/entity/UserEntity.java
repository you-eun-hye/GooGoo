package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String userNickname;

    @Column(unique = true)
    private String userEmail;

    @Column
    private String userPassword;

    @Enumerated(EnumType.STRING)
    private UserRole authority;

    // google 관련
    private String provider;
    private String providerId;

//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany
    private List<CommentEntity> commentEntityList = new ArrayList<>();

//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany
    private List<BoardEntity> boardEntityList = new ArrayList<>();

//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany
    private List<PaymentEntity> paymentEntityList = new ArrayList<>();

    // 회원 생성
    public static UserEntity toUserEntity(UserDTO userDTO, PasswordEncoder passwordEncoder){
        UserEntity userEntity = UserEntity.builder()
                .userNickname(userDTO.getUserNickname())
                .userEmail(userDTO.getUserEmail())
                .userPassword(passwordEncoder.encode((userDTO.getUserPassword())))
                .authority(UserRole.USER)
                .provider(userDTO.getProvider())
                .providerId(userDTO.getProviderId())
                .build();
        return userEntity;
    }

    // 관리자 생성
    public static UserEntity toAdminEntity(UserDTO userDTO, PasswordEncoder passwordEncoder){
        UserEntity userEntity = UserEntity.builder()
                .userNickname(userDTO.getUserNickname())
                .userEmail(userDTO.getUserEmail())
                .userPassword(passwordEncoder.encode((userDTO.getUserPassword())))
                .authority(UserRole.ADMIN)
                .provider(userDTO.getProvider())
                .providerId(userDTO.getProviderId())
                .build();
        return userEntity;
    }

    // 정보 수정
    public static UserEntity toEditUserEntity(UserDTO userDTO, PasswordEncoder passwordEncoder){
        UserEntity userEntity = UserEntity.builder()
                .id(userDTO.getId())
                .userNickname(userDTO.getUserNickname())
                .userEmail(userDTO.getUserEmail())
                .userPassword(passwordEncoder.encode((userDTO.getUserPassword())))
                .authority(userDTO.getAuthority())
                .provider(userDTO.getProvider())
                .providerId(userDTO.getProviderId())
                .build();
        return userEntity;
    }
}
