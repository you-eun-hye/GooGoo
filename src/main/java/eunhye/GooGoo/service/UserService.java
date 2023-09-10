package eunhye.GooGoo.service;

import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import eunhye.GooGoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class  UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    public UserEntity save(UserEntity userEntity){
        return userRepository.save(userEntity);
    }


    // 회원 조회
    public UserDTO findById(UUID id){
        UserDTO userDTO = UserDTO.toUserDTO(userRepository.findById(id));
        return userDTO;
    }

    // 이메일로 회원 조회
    public UserEntity findByUserEmail(String userEmail){
        return userRepository.findByUserEmail(userEmail);
    }

    // 회원 전체 조회
    public List<UserDTO> findUserAll(){
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList){
            if(userEntity.getAuthority() == UserRole.USER){
                userDTOList.add(UserDTO.toUserDTO(userEntity));
            }
        }
        return userDTOList;
    }


    // 관리자 전체 조회
    public List<UserDTO> findAdminAll(){
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList){
            if(userEntity.getAuthority() == UserRole.ADMIN){
                userDTOList.add(UserDTO.toAdminDTO(userEntity));
            }
        }
        return userDTOList;
    }


    // 회원 정보 수정
    public void editUser(UserDTO originalUserDTO, UserDTO userDTO){
        originalUserDTO.setAuthority(UserRole.USER);
        originalUserDTO.setUserNickname(userDTO.getUserNickname());
        originalUserDTO.setUserEmail(userDTO.getUserEmail());
        originalUserDTO.setUserPassword(userDTO.getUserPassword());
        userRepository.save(UserEntity.toEditUserEntity(originalUserDTO, passwordEncoder));
    }


    // 관리자 정보 수정
    public void editAdmin(UserDTO originalUserDTO, UserDTO userDTO){
        originalUserDTO.setAuthority(UserRole.ADMIN);
        originalUserDTO.setUserNickname(userDTO.getUserNickname());
        originalUserDTO.setUserEmail(userDTO.getUserEmail());
        originalUserDTO.setUserPassword(userDTO.getUserPassword());
        userRepository.save(UserEntity.toEditUserEntity(userDTO, passwordEncoder));
    }


    // 회원 탈퇴
    public void deleteById(UUID id) {

        userRepository.deleteById(id);
    }


    // 닉네임 중복 체크
    @Transactional(readOnly = true)
    public boolean checkUserNicknameDuplication(String userNickname) {
        return userRepository.existsByUserNickname(userNickname);
    }


    // 이메일 중복 체크
    @Transactional(readOnly = true)
    public boolean checkUserEmailDuplication(String userEmail) {
        return userRepository.existsByUserEmail(userEmail);
    }


    // 이메일 찾기
    public String findUserEmail(String userNickname){
        UserEntity findUserNickname = userRepository.findByUserNickname(userNickname);
        if(findUserNickname == null){
            return "해당하는 이메일이 없습니다.";
        }
        else{
            return "사용자의 이메일은 " + findUserNickname.getUserEmail() + "입니다.";
        }
    }


    // 비밀번호 재설정
    public void editPassword(String mail, String newPassword) {
        UserEntity userEntity = userRepository.findByUserEmail(mail);
        UserDTO userDTO = UserDTO.toUserDTO(userEntity);
        userDTO.setUserPassword(newPassword);
        userRepository.save(UserEntity.toEditUserEntity(userDTO, passwordEncoder));
    }


    // 회원 수
    public long countUser(){
        return userRepository.countUser();
    }

    // 관리자 수
    public long countAdmin(){
        return userRepository.countUAdmin();
    }

}
