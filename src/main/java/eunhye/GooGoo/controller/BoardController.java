package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public String save(@ModelAttribute BoardDTO boardDTO, @AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
        boardService.save(boardDTO, securityDetails.getUserEntity());
        return "redirect:/user/mypage/board/";
    }

    // 게시물 조회
    @GetMapping("/user/mypage/board")
    public String findAll(Model model, @AuthenticationPrincipal SecurityDetails securityDetails){
        List<BoardDTO> boardDTOList = boardService.findAll(securityDetails.getUserEntity());
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

    // 게시물 삭제
    @GetMapping("/user/mypage/board/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/user/mypage/board/";
    }

}
