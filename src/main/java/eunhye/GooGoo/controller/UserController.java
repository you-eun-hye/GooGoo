package eunhye.GooGoo.controller;

import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @GetMapping("/join")
    public String joinForm() {
        return "user/join";
    }

    @PostMapping( "/join")
    public String join(@Valid UserDTO userDTO) {
        UserEntity userEntity = UserEntity.toUserEntity(userDTO, passwordEncoder);
        userService.save(userEntity);
        return "redirect:/login";
    }

    // 로그인
    @GetMapping("/login")
    public String loginForm(){
        return "user/login";
    }

    @GetMapping("/home")
    public String successLogin() {
        return "home";
    }

    // 마이페이지
    @GetMapping("/user/mypage")
    public String mypageForm(){
        return "user/mypage";
    }

    // 이메일 수정
    @GetMapping("/user/mypage/edit")
    public String editEmailForm(HttpSession session, Model model){
        Long loginId = (Long) session.getAttribute("loginId");
        UserDTO userDTO = userService.selectUser(loginId);
        model.addAttribute("userDTO", userDTO);
        return "user/editEmail";
    }

    @PostMapping("/user/mypage/edit")
    public String editEmail(HttpSession session, @ModelAttribute UserDTO userDTO){
        Long loginId = (Long)session.getAttribute("loginId");
        userDTO.setId(loginId);
        userService.edit(userDTO);
        return "user/mypage";
    }

    // 회원 탈퇴
    @GetMapping("/user/mypage/delete")
    public String deleteById(HttpSession session){
        Long loginId = (Long)session.getAttribute("loginId");
        userService.deleteById(loginId);
        return "user/login";
    }

    // 로그아웃
    @GetMapping("/user/mypage/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "user/login";
    }
}