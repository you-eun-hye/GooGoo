package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.repository.UserRepository;
import eunhye.GooGoo.service.BoardService;
import eunhye.GooGoo.service.EmailService;
import eunhye.GooGoo.service.PaymentService;
import eunhye.GooGoo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final BoardService boardService;
    private final PaymentService paymentService;
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

    // 회원가입 -> 닉네임 중복 체크
    @PostMapping("/checkNickname")
    @ResponseBody
    public boolean checkNickname(@RequestParam("userNickname") String userNickname){
        return userService.checkUserNicknameDuplication(userNickname);
    }

    // 회원가입 -> 이메일 중복 체크
    @PostMapping("/checkEmail")
    @ResponseBody
    public boolean checkEmail(@RequestParam("userEmail") String userEmail){
        return userService.checkUserEmailDuplication(userEmail);
    }

    // 정보수정 -> 닉네임 중복 체크
    @PostMapping("/user/mypage/checkNickname")
    @ResponseBody
    public boolean correctionNickname(@RequestParam("userNickname") String userNickname){
        return userService.checkUserNicknameDuplication(userNickname);
    }

    // 정보수정 -> 이메일 중복 체크
    @PostMapping("/user/mypage/checkEmail")
    @ResponseBody
    public boolean correctionEmail(@RequestParam("userEmail") String userEmail){
        return userService.checkUserEmailDuplication(userEmail);
    }

    // 이메일 인증
    @ResponseBody
    @PostMapping("/mail")
    public String joinMail(String mail){
        String message = "";
            int number = emailService.sendMail(mail);
            message = "" + number;
        return message;
    }

    // 로그인
    @GetMapping("/login")
    public String loginForm(@RequestParam(value="error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "user/login";
    }

    // 이메일 찾기
    @GetMapping("/findUserEmail")
    public String findUserEmailForm(){
        return "user/findUserEmail";
    }

    @PostMapping("/findUserEmail")
    public String findUserEmail(String userNickname, Model model){
        String userEmail = userService.findUserEmail(userNickname);
        model.addAttribute("message", userEmail);
        return "user/findUserEmail";
    }

    // 비밀번호 찾기(비밀번호 재전송)
    @GetMapping("/findUserPassword")
    public String findUserPasswordForm(){
        return "user/findUserPassword";
    }

    @PostMapping("/findUserPassword")
    @ResponseBody
    public void sendNewPassword(String mail){
        String newPassword = emailService.sendNewPassword(mail);
        userService.editPassword(mail, newPassword);
    }

    // 마이페이지
    @GetMapping("/user/mypage")
    public String mypageForm(){
        return "user/mypage";
    }

    // 회원 정보 수정
    @GetMapping("/user/mypage/editUser")
    public String editUserForm(@AuthenticationPrincipal SecurityDetails securityDetails, Model model){
        UserDTO userDTO = userService.findById(securityDetails.getUserEntity().getId());
        model.addAttribute("userDTO", userDTO);
        return "user/editUser";
    }

    @PostMapping("/user/mypage/editUserEmail")
    @ResponseBody
    public String MailSend(String mail){
        String message = "";
        int number = emailService.sendMail(mail);
        message = "" + number;
        return message;
    }

    @PostMapping("/user/mypage/editUser")
    public String editUser(@AuthenticationPrincipal SecurityDetails securityDetails, String userEmail, String userNickname, String userPassword){
        UserDTO userDTO = userService.findById(securityDetails.getUserEntity().getId());
        userService.editUser(userDTO, userEmail, userNickname, userPassword);
        return "user/mypage";
    }

    // 회원 탈퇴
    @GetMapping("/user/mypage/delete")
    public String deleteById(@AuthenticationPrincipal SecurityDetails securityDetails){
        userService.deleteById(securityDetails.getUserEntity().getId());

        List<BoardDTO> boardList = boardService.findAll(securityDetails.getUserEntity());
        for(long i = 0; i < boardList.size(); i++){
            boardService.deleteById(i);
        }

        List<PaymentDTO> paymentList = paymentService.findAll(securityDetails.getUserEntity());
        for(long i = 0; i < paymentList.size(); i++){
            paymentService.deleteById(i);
        }

        return "user/login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(){
        return "/login";
    }
}