package eunhye.GooGoo.controller.RestController;

import eunhye.GooGoo.config.error.CustomException;
import eunhye.GooGoo.config.error.ErrorCode;
import eunhye.GooGoo.config.jwt.JwtTokenProvider;
import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import eunhye.GooGoo.repository.UserRepository;
import eunhye.GooGoo.service.BoardService;
import eunhye.GooGoo.service.EmailService;
import eunhye.GooGoo.service.PaymentService;
import eunhye.GooGoo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;
    private final EmailService emailService;
    private final BoardService boardService;
    private final PaymentService paymentService;
    private final PasswordEncoder passwordEncoder;

    /*
     * 공용
     */

    // 닉네임 중복 체크
    @PostMapping("/checkNickname")
    public boolean checkNickname(@RequestParam("userNickname") String userNickname){
        return userService.checkUserNicknameDuplication(userNickname);
    }

    // 이메일 중복 체크
    @PostMapping("/checkEmail")
    public boolean checkEmail(@RequestParam("userEmail") String userEmail){
        return userService.checkUserEmailDuplication(userEmail);
    }

    // 이메일 인증
    @PostMapping("/mail")
    public String MailSend(String mail){
        String message = "";
        int number = emailService.sendMail(mail);
        message = "" + number;
        return message;
    }

    // 로그인
    @PostMapping("/api/v1/login")
    public String login(@RequestBody Map<String, String> user) {
        UserEntity userEntity = userRepository.findByUserEmail(user.get("userEmail"));
        if (!passwordEncoder.matches(user.get("userPassword"), userEntity.getUserPassword())) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return jwtTokenProvider.createToken(userEntity.getUserEmail(), userEntity.getAuthority());
    }


    /*
     * 사용자
     */

    // 회원 가입
    @PostMapping("/api/v1/join")
    public UUID register(@RequestBody Map<String, String> user) {
        return userRepository.save(UserEntity.builder()
                .userNickname(user.get("userNickname"))
                .userEmail(user.get("userEmail"))
                .userPassword(passwordEncoder.encode(user.get("userPassword")))
                .authority(UserRole.USER)
                .build()).getId();
    }


    // 비밀번호 찾기 (임시 비밀번호 메일 전송)
    @PostMapping("/findUserPassword")
    public void sendNewPassword(String mail){
        String newPassword = emailService.sendNewPassword(mail);
        userService.editPassword(mail, newPassword);
    }
}
