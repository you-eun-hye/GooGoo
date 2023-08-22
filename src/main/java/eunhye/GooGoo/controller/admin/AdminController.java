package eunhye.GooGoo.controller.admin;

import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.service.admin.AdminService;
import eunhye.GooGoo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin")
    public String userAll(Model model) {
        List<UserDTO> userDTOList = adminService.findAll();
        model.addAttribute("userList", userDTOList);
        return "admin/index";
    }

    @GetMapping("/admin/admin")
    public String adminAll() {

        return "admin/admin";
    }
}
