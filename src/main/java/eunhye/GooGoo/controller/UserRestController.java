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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="User Controller", description = "사용자 관련 컨트롤러")
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

    @Operation(summary = "checkNickname", description = "닉네임 중복 체크")
    @GetMapping("/api/nickname")
    public ResponseEntity<Boolean> checkNickname(@Parameter(description = "조회할 닉네임") @RequestParam("userNickname") String userNickname){
        return new ResponseEntity<>(userService.checkUserNicknameDuplication(userNickname), HttpStatus.OK);
    }

    @Operation(summary = "checkEmail", description = "이메일 중복 체크")
    @GetMapping("/api/email")
    public ResponseEntity<Boolean> checkEmail(@Parameter(description = "조회할 이메일") @RequestParam("userEmail") String userEmail){
        return new ResponseEntity<>(userService.checkUserEmailDuplication(userEmail), HttpStatus.OK);
    }

    @Operation(summary = "MailSend", description = "인증 메일 전송")
    @PostMapping("/api/email")
    public ResponseEntity<String> MailSend(@Parameter(description = "메일 보낼 주소") String mail){
        String message = "";
        int number = emailService.sendMail(mail);
        message = "" + number;
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Operation(summary = "loginForm", description = "로그인 페이지")
    @RequestMapping("/login")
    public ModelAndView loginForm(@Parameter(description = "error") @RequestParam(value="error", required = false) String error,
                                  @Parameter(description = "exception") @RequestParam(value = "exception", required = false) String exception,
                            Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        ModelAndView mav = new ModelAndView("user/info/login");
        return mav;
    }

    /*
     * 사용자
     */

    @Operation(summary = "joinUserForm", description = "회원가입 페이지")
    @RequestMapping("/join")
    public ModelAndView joinUserForm() {
        ModelAndView mav = new ModelAndView("user/info/join");
        return mav;
    }

    @Operation(summary = "join", description = "사용자 생성")
    @PostMapping("/api/join")
    public ResponseEntity<?> join(@Parameter(description = "가입된 사용자 정보") @RequestBody UserDTO userDTO) {
        userRepository.save(UserEntity.builder()
                .userNickname(userDTO.getUserNickname())
                .userEmail(userDTO.getUserEmail())
                .userPassword(passwordEncoder.encode(userDTO.getUserPassword()))
                .authority(UserRole.USER)
                .build());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "findUserEmailForm", description = "이메일 찾기 페이지")
    @RequestMapping("/findUserEmail")
    public ModelAndView findUserEmailForm(){
        ModelAndView mav = new ModelAndView("user/info/findUserEmail");
        return mav;
    }

    @Operation(summary = "findUserEmail", description = "이메일 찾은 페이지")
    @GetMapping("/api/userEmail")
    public ModelAndView findUserEmail(@Parameter(description = "찾을 계정의 닉네임") String userNickname, Model model){
        String userEmail = userService.findUserEmail(userNickname);
        model.addAttribute("message", userEmail);
        ModelAndView mav = new ModelAndView("user/info/findUserEmail");
        return mav;
    }


    @Operation(summary = "findUserPasswordForm", description = "비밀번호 찾기 페이지")
    @RequestMapping("/findUserPassword")
    public ModelAndView findUserPasswordForm(){
        ModelAndView mav = new ModelAndView("user/info/findUserPassword");
        return mav;
    }

    @Operation(summary = "sendNewPassword", description = "임시 비밀번호 메일 전송")
    @PostMapping("/api/userPassword")
    public void sendNewPassword(@Parameter(description = "메일 보낼 주소") String mail){
        String newPassword = emailService.sendNewPassword(mail);
        userService.editPassword(mail, newPassword);
    }

    @Operation(summary = "mypage", description = "마이페이지 페이지")
    @RequestMapping("/user/mypage")
    public ModelAndView mypage(){
        ModelAndView mav = new ModelAndView("user/info/mypage");
        return mav;
    }

    @Operation(summary = "editMypageUserForm", description = "회원정보수정 페이지")
    @RequestMapping("/user/mypage/editUser")
    public ModelAndView editMypageUserForm(@Parameter(description = "로그인 중인 사용자") @AuthenticationPrincipal SecurityDetails securityDetails, Model model){
        UserDTO userDTO = userService.findById(securityDetails.getUserEntity().getId());
        model.addAttribute("userDTO", userDTO);
        ModelAndView mav = new ModelAndView("user/info/editUser");
        return mav;
    }

    @Operation(summary = "editMypageUser", description = "회원정보 수정 완료")
    @PatchMapping("/api/editUser")
    public ResponseEntity editMypageUser(@Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails, @Parameter(description = "회원 정보 수정할 내용") @RequestBody UserDTO userDTO){
        UserDTO originalUserDTO = userService.findById(securityDetails.getUserEntity().getId());
        userService.editUser(originalUserDTO, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "logout", description = "로그아웃 후 페이지")
    @RequestMapping("/logout")
    public ModelAndView logout(){
        ModelAndView mav = new ModelAndView("user/info/login");
        return mav;
    }

    @Operation(summary = "deleteMypageUser", description = "회원 탈퇴 후 페이지")
    @RequestMapping("/user/mypage/delete")
    public ModelAndView deleteMypageUser(@Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
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

    @Operation(summary = "saveAdminForm", description = "관리자 생성 페이지")
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

    @Operation(summary = "editAdminForm", description = "관리자 수정 페이지")
    @GetMapping("/admin/admin/edit/{id}")
    public String editAdminForm(@Parameter(description = "수정할 계정 고유 번호") @PathVariable UUID id, Model model){
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

    @Operation(summary = "deleteAdmin", description = "관리자 삭제 후 페이지")
    @GetMapping("/admin/admin/delete/{id}")
    public String deleteAdmin(@Parameter(description = "삭제 할 계정 고유 번호") @PathVariable UUID id){
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

    @Operation(summary = "editAdminUserForm", description = "관리자 정보 수정 페이지")
    @GetMapping("/admin/user/edit/{id}")
    public String editAdminUserForm(@Parameter(description = "수정 할 계정 고유 번호") @PathVariable UUID id, Model model){
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
