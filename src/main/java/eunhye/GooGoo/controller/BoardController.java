package eunhye.GooGoo.controller;

import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/user/mypage/board/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/user/mypage/board/save")
    public String save(@ModelAttribute BoardDTO boardDTO, @ModelAttribute UserDTO userDTO){
        boardService.save(boardDTO, userDTO);
        return "board";
    }
}
