package eunhye.GooGoo.controller.user;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.service.user.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시물 작성
    @GetMapping("/board/save")
    public String saveForm(){
        return "board/save";
    }

    @PostMapping("/board/save")
    public String save(@ModelAttribute BoardDTO boardDTO, @AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
        boardService.save(boardDTO, securityDetails.getUserEntity());
        return "redirect:/board";
    }

    // 게시물 조회
    @GetMapping("/board")
    public String findAll(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model, @AuthenticationPrincipal SecurityDetails securityDetails){
        Page<BoardDTO> list = boardService.paging(pageable, securityDetails.getUserEntity());

        int nowPage = list.getPageable().getPageNumber() +1 ;
        int startPage = Math.max(nowPage -4, 1);
        int endPage = Math.min(nowPage +5, list.getTotalPages());

        model.addAttribute("boardList", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/board";
    }

    // 게시물 상세 조회
    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "board/detail";
    }

    // 게시물 수정
    @GetMapping("/board/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("editBoard", boardDTO);
        return "board/editBoard";
    }

    @PostMapping("/board/edit")
    public String edit(@AuthenticationPrincipal SecurityDetails securityDetails, @ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.edit(boardDTO, securityDetails.getUserEntity());
        model.addAttribute("board", board);
        return "board/detail";
    }

    // 게시물 삭제
    @GetMapping("/board/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.deleteById(id);
        return "redirect:/board";
    }

}
