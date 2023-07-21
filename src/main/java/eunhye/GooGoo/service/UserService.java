package eunhye.GooGoo.service;

import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class  UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // 닉네임 중복 체크
    @Transactional(readOnly = true)
    public boolean checkUserNicknameDuplication(String userNickname) {
        boolean nicknameDuplicate = userRepository.existsByUserNickname(userNickname);
        return nicknameDuplicate;

    }

    // 이메일 중복 체크
    @Transactional(readOnly = true)
    public boolean checkUserEmailDuplication(String userEmail) {
        boolean emailDuplicate = userRepository.existsByUserEmail(userEmail);
        return emailDuplicate;
    }

    // 이메일 찾기
    public String findUserEmail(String userNickname){
        UserEntity findUserNickname = userRepository.findByUserNickname(userNickname);
        return "사용자의 이메일은 " + findUserNickname.getUserEmail() + "입니다.";

    }

    // 비밀번호 재설정
    public void editPassword(UserDTO userDTO, String mail) {
        String newPassword = emailService.sendNewPassword(mail);
        userDTO.setUserPassword(newPassword);
        userRepository.save(UserEntity.toEditUserEntity(userDTO, passwordEncoder));
    }

    // 회원가입
    public UserEntity save(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

//    public void deleteById(Long id) {
//
//        userRepository.deleteById(id);
//    }
}
