package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.JwtTokenProvider;
import eunhye.GooGoo.entity.Role;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    // 회원가입
    @GetMapping("/user/join")
    public String joinForm(){
        return "user/join";
    }

//    @PostMapping("/user/join")
//    public String join(@ModelAttribute UserDTO userDTO){
//        userService.save(userDTO);
//        return "user/login";
//    }
    @PostMapping("/user/join")
    public Long register(@RequestBody Map<String, String> user) {
        return userRepository.save(UserEntity.builder()
                .userEmail(user.get("email"))
                .userPassword(passwordEncoder.encode(user.get("password")))
                .authority(Role.USER)
                .build()).getId();
    }

    // 로그인
    @GetMapping("/user/login")
    public String loginForm(){
        return "user/login";
    }

//    @PostMapping("/user/login")
//    public String login(@ModelAttribute UserDTO userDTO, HttpSession session){
//        UserDTO loginResult = userService.login(userDTO);
//        if(loginResult != null){
//            // login 성공!
//            session.setAttribute("loginId", loginResult.getId());
//            return "home";
//        }else{
//            // login 실패..
//            return "user/login";
//        }
//    }
    @PostMapping("/user/login")
    public String login(@RequestBody Map<String, String> user) {
        UserEntity userEntity = userRepository.findByUserEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));
        if (!passwordEncoder.matches(user.get("password"), userEntity.getUserPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        return jwtTokenProvider.createToken(userEntity.getUserEmail(), userEntity.getAuthority());
    }

    // 마이페이지
    @GetMapping("/user/mypage")
    public String mypageForm(){
        return "user/mypage";
    }

//    // 이메일 수정
//    @GetMapping("/user/mypage/edit")
//    public String editEmailForm(HttpSession session, Model model){
//        Long loginId = (Long) session.getAttribute("loginId");
//        UserDTO userDTO = userService.selectUser(loginId);
//        model.addAttribute("userDTO", userDTO);
//        return "user/editEmail";
//    }

//    @PostMapping("/user/mypage/edit")
//    public String editEmail(HttpSession session, @ModelAttribute UserDTO userDTO){
//        Long loginId = (Long)session.getAttribute("loginId");
//        userDTO.setId(loginId);
//        userService.edit(userDTO);
//        return "user/mypage";
//    }
//
//    // 회원 탈퇴
//    @GetMapping("/user/mypage/delete")
//    public String deleteById(HttpSession session){
//        Long loginId = (Long)session.getAttribute("loginId");
//        userService.deleteById(loginId);
//        return "user/login";
//    }

    // 로그아웃
    @GetMapping("/user/mypage/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "user/login";
    }
}
