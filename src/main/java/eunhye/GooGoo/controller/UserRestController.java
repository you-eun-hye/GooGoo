package eunhye.GooGoo.controller;

import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import eunhye.GooGoo.repository.UserRepository;
import eunhye.GooGoo.service.EmailService;
import eunhye.GooGoo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserRepository userRepository;

    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    /*
     * 공용
     */

    // 닉네임 중복 체크
    @GetMapping("/checkNickname")
    public boolean checkNickname(@RequestParam("userNickname") String userNickname){
        return userService.checkUserNicknameDuplication(userNickname);
    }

    // 이메일 중복 체크
    @GetMapping("/checkEmail")
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
    @GetMapping("/login")
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
    @GetMapping("/join")
    public ModelAndView joinUserForm() {
        ModelAndView mav = new ModelAndView("user/info/join");
        return mav;
    }

    @PostMapping("/api/join")
    public HttpStatus join(@RequestBody UserDTO userDTO) {
        userRepository.save(UserEntity.builder()
                .userNickname(userDTO.getUserNickname())
                .userEmail(userDTO.getUserEmail())
                .userPassword(passwordEncoder.encode(userDTO.getUserPassword()))
                .authority(UserRole.USER)
                .build());
        return HttpStatus.CREATED;
    }

    // 이메일 찾기
    @GetMapping("/findUserEmail")
    public ModelAndView findUserEmailForm(){
        ModelAndView mav = new ModelAndView("user/info/findUserEmail");
        return mav;
    }

//    @PostMapping("/findUserEmail")
//    public String findUserEmail(String userNickname, Model model){
//        String userEmail = userService.findUserEmail(userNickname);
//        model.addAttribute("message", userEmail);
//        return "user/info/findUserEmail";
//    }


    // 비밀번호 찾기 (임시 비밀번호 메일 전송)
    @GetMapping("/findUserPassword")
    public ModelAndView findUserPasswordForm(){
        ModelAndView mav = new ModelAndView("user/info/findUserPassword");
        return mav;
    }

    @PostMapping("/findUserPassword")
    public void sendNewPassword(String mail){
        String newPassword = emailService.sendNewPassword(mail);
        userService.editPassword(mail, newPassword);
    }

    // 마이페이지
    @GetMapping("/user/mypage")
    public ModelAndView mypage(){
        ModelAndView mav = new ModelAndView("user/info/mypage");
        return mav;
    }

    // 회원 정보 수정
//    @GetMapping("/user/mypage/editUser")
//    public String editMypageUserForm(@AuthenticationPrincipal SecurityDetails securityDetails, Model model){
//        UserDTO userDTO = userService.findById(securityDetails.getUserEntity().getId());
//        model.addAttribute("userDTO", userDTO);
//        return "user/info/editUser";
//    }
//
//    // 회원 정보 수정
//    @PostMapping("/user/mypage/editUser")
//    public String editMypageUser(@AuthenticationPrincipal SecurityDetails securityDetails, String userEmail, String userNickname, String userPassword){
//        UserDTO userDTO = userService.findById(securityDetails.getUserEntity().getId());
//        userService.editUser(userDTO, userEmail, userNickname, userPassword);
//        return "user/info/mypage";
//    }

    // 로그아웃
    @GetMapping("/logout")
    public ModelAndView logout(){
        ModelAndView mav = new ModelAndView("user/info/login");
        return mav;
    }

    // 회원 탈퇴
//    @GetMapping("/user/mypage/delete")
//    public String deleteMypageUser(@AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
//        userService.deleteById(securityDetails.getUserEntity().getId());
//
//        List<BoardDTO> boardList = boardService.findUserBoard(securityDetails.getUserEntity().getId());
//        for(long i = 0; i < boardList.size(); i++){
//            boardService.deleteById(i);
//        }
//
//        List<PaymentDTO> paymentList = paymentService.findAll(securityDetails.getUserEntity().getId());
//        for(long i = 0; i < paymentList.size(); i++){
//            paymentService.deleteById(i);
//        }
//
//        return "user/info/login";
//    }

    /*
     * 관리자
     */

    // 관리자 생성
    @GetMapping("/admin/admin/join")
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
    public String editAdminForm(@PathVariable Long id, Model model){
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("user", userDTO);
        return "admin/edit";
    }

    @PostMapping("/admin/admin/edit")
    public String editAdmin(Long id, String userEmail, String userNickname, String userPassword){
        UserDTO userDTO = userService.findById(id);
        userService.editAdmin(userDTO, userEmail, userNickname, userPassword);
        return "redirect:/admin/admin";
    }

    // 관리자 삭제
    @GetMapping("/admin/admin/delete/{id}")
    public String deleteAdmin(@PathVariable Long id){
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
    public String editAdminUserForm(@PathVariable Long id, Model model){
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
