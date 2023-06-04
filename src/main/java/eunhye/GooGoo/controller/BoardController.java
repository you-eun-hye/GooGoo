package eunhye.GooGoo.controller;

import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시물 작성
    @GetMapping("/user/mypage/board/save")
    public String saveForm(){
        return "board/save";
    }

    @PostMapping("/user/mypage/board/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO);
        return "user/mypage";
    }

    // 게시물 조회
    @GetMapping("/user/mypage/board")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "board/board";
    }

    // 게시물 상세 조회
    @GetMapping("/user/mypage/board/{id}")
    public String findById(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "board/detail";
    }

    // 게시물 수정
    @GetMapping("/user/mypage/board/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("editBoard", boardDTO);
        return "board/editBoard";
    }

    @PostMapping("/user/mypage/board/edit")
    public String edit(@ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.edit(boardDTO);
        model.addAttribute("board", board);
        return "board/detail";
    }
}
