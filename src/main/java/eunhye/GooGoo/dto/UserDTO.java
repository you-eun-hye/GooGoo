package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Schema(description = "ID", example = "계정 고유 번호")
    private UUID id;

    @Schema(description = "닉네임", example = "계정 닉네임")
    private String userNickname;

    @Schema(description = "이메일", example = "계정 이메일")
    private String userEmail;

    @Schema(description = "비밀번호", example = "계정 비밀번호")
    private String userPassword;

    @Schema(description = "생성일", example = "계정 생성일")
    private LocalDateTime userCreatedTime;

    @Schema(description = "권한", example = "계정 권한")
    private UserRole authority;

    @Schema(description = "구글", example = "구글 로그인임을 알리는 컬럼")
    private String provider;

    @Schema(description = "구글 고유 번호", example = "구글 고유 번호")
    private String providerId;

    // Entity -> DTO 회원용
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

    // Entity -> DTO 관리자용
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