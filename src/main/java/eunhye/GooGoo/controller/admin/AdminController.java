package eunhye.GooGoo.controller.admin;

import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.service.admin.AdminService;
import eunhye.GooGoo.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // 관리자 생성
    @GetMapping("/admin/admin/join")
    public String adminJoinForm(){
        return "admin/join";
    }

    @PostMapping("/admin/admin/join")
    public String adminJoin(@Valid UserDTO userDTO){
        UserEntity userEntity = UserEntity.toAdminEntity(userDTO, passwordEncoder);
        userService.save(userEntity);
        return "admin/index";
    }

    // 관리자 조회
    @GetMapping("/admin/admin")
    public String adminAll(Model model) {
        List<UserDTO> adminDTOList = adminService.findAdminAll();
        model.addAttribute("adminList", adminDTOList);
        return "admin/admin";
    }

    // 관리자 삭제
    @GetMapping("/admin/admin/delete/{id}")
    public String adminDelete(@PathVariable Long id){
        userService.deleteById(id);
        return "admin/admin";
    }
}
