package eunhye.GooGoo.service;

import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class  UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // 회원가입
    public UserEntity save(UserEntity userEntity){

        return userRepository.save(userEntity);
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

    // 회원 정보 수정
    public UserDTO findById(Long id){
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if(optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
            UserDTO userDTO = UserDTO.toUserDTO(userEntity);
            return userDTO;
        }else{
            return null;
        }
    }

    public void editUser(UserDTO userDTO, String userEmail, String userNickname, String userPassword){
        userDTO.setUserNickname(userNickname);
        userDTO.setUserEmail(userEmail);
        userDTO.setUserPassword(userPassword);
        userRepository.save(UserEntity.toEditUserEntity(userDTO, passwordEncoder));
    }

    // 회원 탈퇴
    public void deleteById(Long id) {

        userRepository.deleteById(id);
    }
}
