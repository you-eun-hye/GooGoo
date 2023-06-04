package eunhye.GooGoo.controller;

import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @GetMapping("/user/join")
    public String joinForm(){
        return "user/join";
    }

    @PostMapping("/user/join")
    public String join(@ModelAttribute UserDTO userDTO){
        userService.save(userDTO);
        return "user/login";
    }

    // 로그인
    @GetMapping("/user/login")
    public String loginForm(){
        return "user/login";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session){
        UserDTO loginResult = userService.login(userDTO);
        if(loginResult != null){
            // login 성공!
            session.setAttribute("loginEmail", loginResult.getUserEmail());
            return "home";
        }else{
            // login 실패..
            return "user/login";
        }
    }

    // 마이페이지
    @GetMapping("/user/mypage")
    public String mypageForm(){
        return "user/mypage";
    }

    // 이메일 수정
    @GetMapping("/user/mypage/edit")
    public String editEmailForm(HttpSession session, Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        UserDTO userDTO = userService.editEmailForm(myEmail);
        model.addAttribute("editUser", userDTO);
        return "user/editEmail";
    }

    @PostMapping("/user/mypage/edit")
    public String editEmail(@ModelAttribute UserDTO userDTO){
        userService.edit(userDTO);
        return "user/mypage";
    }

    // 회원 탈퇴
    // id null error
    @GetMapping("/user/delete/{id}")
    public String deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return "user/login";
    }

    // 로그아웃
    @GetMapping("/user/mypage/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "user/login";
    }
}
