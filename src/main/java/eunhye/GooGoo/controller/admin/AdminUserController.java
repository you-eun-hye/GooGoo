package eunhye.GooGoo.controller.admin;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.repository.UserRepository;
import eunhye.GooGoo.service.admin.AdminService;
import eunhye.GooGoo.service.user.BoardService;
import eunhye.GooGoo.service.user.PaymentService;
import eunhye.GooGoo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminService adminService;
    private final UserService userService;
    private final BoardService boardService;
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    // 회원 조회
    @GetMapping("/admin")
    public String userAll(Model model, @AuthenticationPrincipal SecurityDetails securityDetails) {
        List<UserDTO> userDTOList = adminService.findUserAll();
        model.addAttribute("userList", userDTOList);
        model.addAttribute("countUser", adminService.countUser());
        model.addAttribute("adminName", securityDetails.getUserEntity().getUserEmail());
        return "admin/index";
    }

    // 회원 수정
    @GetMapping("/admin/user/edit/{id}")
    public String userEditForm(@PathVariable Long id, Model model){
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("user", userDTO);
        return "admin/edit";
    }

    @PostMapping("/admin/user/edit")
    public String userEdit(String userEmail, String userNickname, String userPassword){
        UserEntity userEntity = userRepository.findByUserEmail(userEmail);
        UserDTO userDTO = userService.findById(userEntity.getId());
        userService.editUser(userDTO, userEmail, userNickname, userPassword);
        return "redirect:admin/index";
    }

    // 회원 삭제
    @GetMapping("/admin/user/delete/{id}")
    public String deleteById(@PathVariable Long id) throws IOException {
        userService.deleteById(id);

        List<BoardDTO> boardList = boardService.findUserBoard(id);
        for (long i = 0; i < boardList.size(); i++) {
            boardService.deleteById(i);
        }

        List<PaymentDTO> paymentList = paymentService.findAll(id);
        for (long i = 0; i < paymentList.size(); i++) {
            paymentService.deleteById(i);
        }
        return "admin/index";
    }

}
