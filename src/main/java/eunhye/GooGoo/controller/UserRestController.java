package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import eunhye.GooGoo.repository.UserRepository;
import eunhye.GooGoo.service.BoardService;
import eunhye.GooGoo.service.EmailService;
import eunhye.GooGoo.service.PaymentService;
import eunhye.GooGoo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserRepository userRepository;

    private final UserService userService;
    private final BoardService boardService;
    private final EmailService emailService;
    private final PaymentService paymentService;
    private final PasswordEncoder passwordEncoder;

    /*
     * 공용
     */

    // 닉네임 중복 체크
    @GetMapping("/api/nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam("userNickname") String userNickname){
        return new ResponseEntity<>(userService.checkUserNicknameDuplication(userNickname), HttpStatus.OK);
    }

    // 이메일 중복 체크
    @GetMapping("/api/email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam("userEmail") String userEmail){
        return new ResponseEntity<>(userService.checkUserEmailDuplication(userEmail), HttpStatus.OK);
    }

    // 이메일 인증
    @PostMapping("/api/email")
    public ResponseEntity<String> MailSend(String mail){
        String message = "";
        int number = emailService.sendMail(mail);
        message = "" + number;
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 로그인
    @RequestMapping("/login")
    public ModelAndView loginForm(@RequestParam(value="error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        ModelAndView mav = new ModelAndView("user/info/login");
        return mav;
    }

    /*
     * 사용자
     */

    // 회원 가입
    @RequestMapping("/join")
    public ModelAndView joinUserForm() {
        ModelAndView mav = new ModelAndView("user/info/join");
        return mav;
    }

    @PostMapping("/api/join")
    public ResponseEntity<?> join(@RequestBody UserDTO userDTO) {
        userRepository.save(UserEntity.builder()
                .userNickname(userDTO.getUserNickname())
                .userEmail(userDTO.getUserEmail())
                .userPassword(passwordEncoder.encode(userDTO.getUserPassword()))
                .authority(UserRole.USER)
                .build());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 이메일 찾기
    @RequestMapping("/findUserEmail")
    public ModelAndView findUserEmailForm(){
        ModelAndView mav = new ModelAndView("user/info/findUserEmail");
        return mav;
    }

    @GetMapping("/api/userEmail")
    public ModelAndView findUserEmail(String userNickname, Model model){
        String userEmail = userService.findUserEmail(userNickname);
        model.addAttribute("message", userEmail);
        ModelAndView mav = new ModelAndView("user/info/findUserEmail");
        return mav;
    }


    // 비밀번호 찾기 (임시 비밀번호 메일 전송)
    @RequestMapping("/findUserPassword")
    public ModelAndView findUserPasswordForm(){
        ModelAndView mav = new ModelAndView("user/info/findUserPassword");
        return mav;
    }

    @PostMapping("/api/userPassword")
    public void sendNewPassword(String mail){
        String newPassword = emailService.sendNewPassword(mail);
        userService.editPassword(mail, newPassword);
    }

    // 마이페이지
    @RequestMapping("/user/mypage")
    public ModelAndView mypage(){
        ModelAndView mav = new ModelAndView("user/info/mypage");
        return mav;
    }

    // 회원 정보 수정
    @RequestMapping("/user/mypage/editUser")
    public ModelAndView editMypageUserForm(@AuthenticationPrincipal SecurityDetails securityDetails, Model model){
        UserDTO userDTO = userService.findById(securityDetails.getUserEntity().getId());
        model.addAttribute("userDTO", userDTO);
        ModelAndView mav = new ModelAndView("user/info/editUser");
        return mav;
    }

    // 회원 정보 수정
    @PatchMapping("/api/editUser")
    public ResponseEntity editMypageUser(@AuthenticationPrincipal SecurityDetails securityDetails, @RequestBody UserDTO userDTO){
        UserDTO originalUserDTO = userService.findById(securityDetails.getUserEntity().getId());
        userService.editUser(originalUserDTO, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그아웃
    @RequestMapping("/logout")
    public ModelAndView logout(){
        ModelAndView mav = new ModelAndView("user/info/login");
        return mav;
    }

    // 회원 탈퇴
    @RequestMapping("/user/mypage/delete")
    public ModelAndView deleteMypageUser(@AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
        userService.deleteById(securityDetails.getUserEntity().getId());

        List<BoardDTO> boardList = boardService.findUserBoard(securityDetails.getUserEntity().getId());
        for(long i = 0; i < boardList.size(); i++){
            boardService.deleteById(i);
        }

        List<PaymentDTO> paymentList = paymentService.findAll(securityDetails.getUserEntity().getId());
        for(long i = 0; i < paymentList.size(); i++){
            paymentService.deleteById(i);
        }
        ModelAndView mav = new ModelAndView("user/info/login");
        return mav;
    }

    /*
     * 관리자
     */

    // 관리자 생성
    @RequestMapping("/admin/admin/join")
    public ModelAndView saveAdminForm(){
        ModelAndView mav = new ModelAndView("admin/join");
        return mav;
    }

//    @PostMapping("/admin/admin/join")
//    public String saveAdmin(@Valid UserDTO userDTO){
//        UserEntity userEntity = UserEntity.toAdminEntity(userDTO, passwordEncoder);
//        userService.save(userEntity);
//        return "admin/index";
//    }

    // 관리자 조회
//    @GetMapping("/admin/admin")
//    public String findAllAdmin(Model model, @AuthenticationPrincipal SecurityDetails securityDetails) {
//        List<UserDTO> adminDTOList = userService.findAdminAll();
//        model.addAttribute("adminList", adminDTOList);
//        model.addAttribute("countAdmin", userService.countAdmin());
//        model.addAttribute("adminName", securityDetails.getUserEntity().getUserEmail());
//        return "admin/admin";
//    }

    // 관리자 정보 수정
    @GetMapping("/admin/admin/edit/{id}")
    public String editAdminForm(@PathVariable UUID id, Model model){
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("user", userDTO);
        return "admin/edit";
    }

//    @PostMapping("/admin/admin/edit")
//    public String editAdmin(UUID id, String userEmail, String userNickname, String userPassword){
//        UserDTO userDTO = userService.findById(id);
//        userService.editAdmin(userDTO, userEmail, userNickname, userPassword);
//        return "redirect:/admin/admin";
//    }

    // 관리자 삭제
    @GetMapping("/admin/admin/delete/{id}")
    public String deleteAdmin(@PathVariable UUID id){
        userService.deleteById(id);
        return "redirect:/admin/admin";
    }

    // 회원 조회
//    @GetMapping("/admin")
//    public String findAllUser(Model model, @AuthenticationPrincipal SecurityDetails securityDetails) {
//        List<UserDTO> userDTOList = userService.findUserAll();
//        model.addAttribute("userList", userDTOList);
//        model.addAttribute("countUser", userService.countUser());
//        model.addAttribute("adminName", securityDetails.getUserEntity().getUserEmail());
//        return "admin/user/index";
//    }

    // 회원 수정
    @GetMapping("/admin/user/edit/{id}")
    public String editAdminUserForm(@PathVariable UUID id, Model model){
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("user", userDTO);
        return "admin/user/edit";
    }

//    @PostMapping("/admin/user/edit")
//    public String editAdminUser(String userEmail, String userNickname, String userPassword){
//        UserEntity userEntity = userService.findByUserEmail(userEmail);
//        UserDTO userDTO = userService.findById(userEntity.getId());
//        userService.editUser(userDTO, userEmail, userNickname, userPassword);
//        return "redirect:/admin";
//    }

    // 회원 삭제
//    @GetMapping("/admin/user/delete/{id}")
//    public String deleteAdminUser(@PathVariable Long id) throws IOException {
//        userService.deleteById(id);
//
//        List<BoardDTO> boardList = boardService.findUserBoard(id);
//        for (long i = 0; i < boardList.size(); i++) {
//            boardService.deleteById(i);
//        }
//
//        List<PaymentDTO> paymentList = paymentService.findAll(id);
//        for (long i = 0; i < paymentList.size(); i++) {
//            paymentService.deleteById(i);
//        }
//        return "admin/user/index";
//    }
}
