package eunhye.GooGoo.controller.admin;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.service.admin.AdminService;
import eunhye.GooGoo.service.user.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminNotiController {
    private final AdminService adminService;
    private final BoardService boardService;

    // 공지 조회
    @GetMapping("/admin/noti")
    public String notiAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findNoti();
        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("boardListCount", boardDTOList.size());
        return "admin/noti/noti";
    }

    // 공지 상세 조회
    @GetMapping("/admin/noti/detail/{id}")
    public String notiDetail(@PathVariable Long id, @AuthenticationPrincipal SecurityDetails securityDetails, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "admin/noti/detail";
    }

    // 공지 생성
    @GetMapping("/admin/noti/save")
    public String notiSave() {
        return "admin/noti/save";
    }

    @PostMapping("/admin/noti/save")
    public String save(@ModelAttribute BoardDTO boardDTO, @AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
        adminService.saveNoti(boardDTO, securityDetails.getUserEntity());
        return "redirect:/admin/noti";
    }

    // 공지 수정
    @GetMapping("/admin/noti/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("editBoard", boardDTO);
        return "/admin/noti/editBoard";
    }

    @PostMapping("/admin/noti/edit")
    public String edit(@AuthenticationPrincipal SecurityDetails securityDetails, @ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.edit(boardDTO, securityDetails.getUserEntity());
        model.addAttribute("board", board);
        return "/admin/noti/detail";
    }

    // 공지 삭제
    @GetMapping("/admin/noti/delete/{id}")
    public String delete(@PathVariable Long id) throws IOException {
        boardService.deleteById(id);
        return "redirect:/admin/noti";
    }
}
