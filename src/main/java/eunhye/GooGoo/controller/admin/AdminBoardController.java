package eunhye.GooGoo.controller.admin;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.CommentDTO;
import eunhye.GooGoo.service.admin.AdminService;
import eunhye.GooGoo.service.user.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminBoardController {
    private final BoardService boardService;
    private final AdminService adminService;

    // 문의글 조회
    @GetMapping("/admin/board")
    public String boardAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findBoard();
        model.addAttribute("boardList", boardDTOList);
//        List<UserDTO> userDTOList = new ArrayList<>();
//        for(BoardDTO boardDTO : boardDTOList){
//            userDTOList.add(UserDTO.toUserDTO(boardDTO.getUserEntity()));
//        }
//        model.addAttribute("userList", userDTOList);
        return "admin/board/board";
    }

    // 문의글 답변
    @GetMapping("/admin/board/detail/{id}")
    public String boardDetail(@PathVariable Long id, @AuthenticationPrincipal SecurityDetails securityDetails, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("loginAdmin", securityDetails.getUserEntity());
        return "admin/board/detail";
    }

    @PostMapping("/admin/board/detail/comment")
    public ResponseEntity comment(@ModelAttribute CommentDTO commentDTO){
        System.out.println("commentDTO" + commentDTO);
        Long saveResult = adminService.save(commentDTO);
        if(saveResult != null){
            List<CommentDTO> commentDTOList = adminService.findCommentAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
