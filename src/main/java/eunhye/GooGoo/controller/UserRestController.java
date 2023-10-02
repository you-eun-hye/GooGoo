package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.dto.UserDTO;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@Tag(name="User Controller", description = "사용자 관련 컨트롤러")
public class UserRestController {

    private final UserService userService;
    private final BoardService boardService;
    private final EmailService emailService;
    private final PaymentService paymentService;

    /*
     * 공용
     */

    @Operation(summary = "duplication-nickname", description = "닉네임 중복 체크")
    @ResponseBody
    @GetMapping("/api/duplication-nickname")
    public ResponseEntity<Boolean> checkNickname(@Parameter(description = "조회할 닉네임") @RequestParam("userNickname") String userNickname){
        return new ResponseEntity<>(userService.checkUserNicknameDuplication(userNickname), HttpStatus.OK);
    }

    @Operation(summary = "duplication-email", description = "이메일 중복 체크")
    @ResponseBody
    @GetMapping("/api/duplication-email")
    public ResponseEntity<Boolean> checkEmail(@Parameter(description = "조회할 이메일") @RequestParam("userEmail") String userEmail){
        return new ResponseEntity<>(userService.checkUserEmailDuplication(userEmail), HttpStatus.OK);
    }

    @Operation(summary = "certification-number", description = "인증 메일 전송")
    @ResponseBody
    @PostMapping("/api/certification-number")
    public ResponseEntity<String> MailSend(@Parameter(description = "메일 보낼 주소") String mail){
        String message = "";
        int number = emailService.sendMail(mail);
        message = "" + number;
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Operation(summary = "login", description = "로그인 페이지")
    @GetMapping("/login")
    public String loginForm(@Parameter(description = "error") @RequestParam(value="error", required = false) String error,
                            @Parameter(description = "exception") @RequestParam(value = "exception", required = false) String exception,
                            Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "user/info/login";
    }

    /*
     * 사용자
     */

    @Operation(summary = "joinForm", description = "회원가입 페이지")
    @GetMapping("/join")
    public String joinUserForm() {
        return "user/info/join";
    }

    @Operation(summary = "join", description = "사용자 생성")
    @ResponseBody
    @PostMapping("/api/join")
    public ResponseEntity join(@Parameter(description = "가입된 사용자 정보") @RequestBody UserDTO userDTO) {
        userService.userSave(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "findEmail", description = "이메일 찾기 페이지")
    @GetMapping("/email")
    public String findUserEmailForm(){
        return "user/info/email";
    }

    @Operation(summary = "finded-email", description = "이메일 찾은 페이지")
    @ResponseBody
    @GetMapping("/api/email")
    public ResponseEntity<String> findUserEmail(@Parameter(description = "찾을 계정의 닉네임") String userNickname){
        String userEmail = userService.findUserEmail(userNickname);
        return new ResponseEntity<>(userEmail, HttpStatus.OK);
    }

    @Operation(summary = "findPassword", description = "비밀번호 찾기 페이지")
    @GetMapping("/password")
    public String findUserPasswordForm(){
        return "password";
    }

    @Operation(summary = "temporary-password", description = "임시 비밀번호 메일 전송")
    @ResponseBody
    @PostMapping("/api/password")
    public void sendNewPassword(@Parameter(description = "메일 보낼 주소") String mail){
        String newPassword = emailService.sendNewPassword(mail);
        userService.editPassword(mail, newPassword);
    }

    @Operation(summary = "mypage", description = "마이페이지 페이지")
    @GetMapping("/user/mypage")
    public String mypage(){
        return "user/info/mypage";
    }

    @Operation(summary = "editMypageUserForm", description = "회원정보수정 페이지")
    @GetMapping("/user/mypage/user")
    public String editMypageUserForm(@Parameter(description = "로그인 중인 사용자") @AuthenticationPrincipal SecurityDetails securityDetails, Model model){
        UserDTO userDTO = userService.findById(securityDetails.getUserEntity().getId());
        model.addAttribute("userDTO", userDTO);
        return "user/info/editUser";
    }

    // 에러 발생
    @Operation(summary = "editMypageUser", description = "회원정보 수정 완료")
    @ResponseBody
    @PatchMapping("/api/user")
    public ResponseEntity editMypageUser(@Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails, @Parameter(description = "회원 정보 수정할 내용") @RequestBody UserDTO userDTO){
        UserDTO originalUserDTO = userService.findById(securityDetails.getUserEntity().getId());
        userService.editUser(originalUserDTO, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "afterLogout", description = "로그아웃 후 페이지")
    @GetMapping("/logout")
    public String logout(){
        return "user/info/login";
    }

    @Operation(summary = "deleteMypageUser", description = "회원 탈퇴 후 페이지")
    @ResponseBody
    @DeleteMapping("/api/user")
    public ResponseEntity deleteMypageUser(@Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
        userService.deleteById(securityDetails.getUserEntity().getId());

        List<BoardDTO> boardList = boardService.findUserBoard(securityDetails.getUserEntity().getId());
        for(long i = 0; i < boardList.size(); i++){
            boardService.deleteById(i);
        }

        List<PaymentDTO> paymentList = paymentService.findAll(securityDetails.getUserEntity().getId());
        for(long i = 0; i < paymentList.size(); i++){
            paymentService.deleteById(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 관리자
     */

    @Operation(summary = "saveAdminForm", description = "관리자 생성 페이지")
    @GetMapping("/admin/join")
    public String saveAdminForm(){
        return "admin/join";
    }

    @Operation(summary = "saveAdmin", description = "관리자 계정 생성 후 페이지")
    @ResponseBody
    @PostMapping("/api/admin/join")
    public ResponseEntity saveAdmin(@Parameter(description = "생성할 계정 정보") @RequestBody UserDTO userDTO){
        userService.adminSave(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "findAllAdmin", description = "관리자 전체 조회")
    @GetMapping("/admin")
    public String findAllAdmin(Model model, @Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails) {
        List<UserDTO> adminDTOList = userService.findAdminAll();
        model.addAttribute("adminList", adminDTOList);
        model.addAttribute("countAdmin", userService.countAdmin());
        model.addAttribute("adminName", securityDetails.getUserEntity().getUserEmail());
        return "admin/admin";
    }

    @Operation(summary = "editAdminForm", description = "관리자 수정 페이지")
    @GetMapping("/admin/{id}")
    public String editAdminForm(@Parameter(description = "수정할 계정 고유 번호") @PathVariable UUID id, Model model){
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("user", userDTO);
        return "admin/edit";
    }

    // 에러 발생
    @Operation(summary = "editAdmin", description = "관리자 정보 수정")
    @ResponseBody
    @PatchMapping("/api/admin")
    public ResponseEntity editAdmin(@Parameter(description = "수정할 계정 고유 번호") UUID id, @Parameter(description = "회원 정보 수정할 내용") @RequestBody UserDTO userDTO){
        UserDTO originalUserDTO = userService.findById(id);
        userService.editAdmin(originalUserDTO, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "deleteAdmin", description = "관리자 삭제 후 페이지")
    @ResponseBody
    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity deleteAdmin(@Parameter(description = "삭제 할 계정 고유 번호") @PathVariable UUID id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "findAllUser", description = "유저 전체 조회")
    @GetMapping("/admin/user")
    public String findAllUser(Model model, @Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails) {
        List<UserDTO> userDTOList = userService.findUserAll();
        model.addAttribute("userList", userDTOList);
        model.addAttribute("countUser", userService.countUser());
        model.addAttribute("adminName", securityDetails.getUserEntity().getUserEmail());
        return "admin/user/index";
    }

    @Operation(summary = "editAdminUserForm", description = "유저 정보 강제 수정 페이지")
    @GetMapping("/admin/user/{id}")
    public String editAdminUserForm(@Parameter(description = "수정 할 계정 고유 번호") @PathVariable UUID id, Model model){
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("user", userDTO);
        return "admin/user/edit";
    }

    @Operation(summary = "editAdminUser", description = "유저 정보 강제 수정 후 페이지")
    @ResponseBody
    @PatchMapping("/api/admin/user")
    public ResponseEntity editAdminUser(@Parameter(description = "유저 정보 강제 수정할 내용") @RequestBody UserDTO userDTO){
        UserDTO originalUserDTO = userService.findById(userDTO.getId());
        userService.editUser(originalUserDTO, userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "deleteAdminUser", description = "회원 강제 탈퇴 후 페이지")
    @ResponseBody
    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity deleteAdminUser(@Parameter(description = "삭제할 회원 고유 번호") @PathVariable UUID id) throws IOException {
        userService.deleteById(id);

        List<BoardDTO> boardList = boardService.findUserBoard(id);
        for (long i = 0; i < boardList.size(); i++) {
            boardService.deleteById(i);
        }

        List<PaymentDTO> paymentList = paymentService.findAll(id);
        for (long i = 0; i < paymentList.size(); i++) {
            paymentService.deleteById(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
