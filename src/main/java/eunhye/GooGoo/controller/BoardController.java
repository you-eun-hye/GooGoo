package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.CommentDTO;
import eunhye.GooGoo.service.BoardService;
import eunhye.GooGoo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final CommentService commentService;

    /*
    * 사용자
    */

    // 문의글 작성
    @GetMapping("/board/save")
    public String saveQuestionForm(){
        return "user/board/save";
    }

//    @PostMapping("/board/save")
//    public String saveQuestion(@ModelAttribute BoardDTO boardDTO, @AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
//        boardService.save(boardDTO, securityDetails.getUserEntity());
//        return "redirect:/board";
//    }

    // 내가 쓴 문의글 5개씩 최신순 페이징 조회
//    @GetMapping("/board")
//    public String findMyQuestion(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model, @AuthenticationPrincipal SecurityDetails securityDetails){
//        Page<BoardDTO> list = boardService.paging(pageable, securityDetails.getUserEntity());
//
//        int nowPage = list.getPageable().getPageNumber() +1;
//        int startPage = Math.max(nowPage -4, 1);
//        int endPage = Math.min(nowPage +5, list.getTotalPages());
//
//        model.addAttribute("boardList", list);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//
//        return "user/board/board";
//    }

    // 문의글 상세 조회
    @GetMapping("/board/{id}")
    public String findDetailQuestion(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        List<CommentDTO> commentDTOList = commentService.findCommentAll(id);
        model.addAttribute("commentList", commentDTOList);
        return "user/board/detail";
    }

    // 문의글 수정
    @GetMapping("/board/edit/{id}")
    public String editQuestionForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("editBoard", boardDTO);
        return "user/board/editBoard";
    }

//    @PostMapping("/board/edit")
//    public String editQuestion(@AuthenticationPrincipal SecurityDetails securityDetails, @ModelAttribute BoardDTO boardDTO, Model model){
//        BoardDTO board = boardService.edit(boardDTO, securityDetails.getUserEntity());
//        model.addAttribute("board", board);
//        return "redirect:/board";
//    }

    // 문의글 삭제
    @GetMapping("/board/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) throws IOException {
        boardService.deleteById(id);
        return "redirect:/board";
    }

    /*
    * 관리자
    */

    // 문의글 조회
//    @GetMapping("/admin/board")
//    public String findAllQuestion(Model model, @AuthenticationPrincipal SecurityDetails securityDetails) {
//        List<BoardDTO> boardDTOList = boardService.findBoard();
//        model.addAttribute("boardList", boardDTOList);
//        model.addAttribute("waitComment", boardService.waitComment());
//        model.addAttribute("adminName", securityDetails.getUserEntity().getUserEmail());
//        return "admin/board/board";
//    }

    // 문의글 상세 조회 및 답변
    @GetMapping("/admin/board/detail/{id}")
    public String commentForm(@PathVariable Long id, @AuthenticationPrincipal SecurityDetails securityDetails, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        List<CommentDTO> commentDTOList = commentService.findCommentAll(id);
        model.addAttribute("commentList", commentDTOList);

        model.addAttribute("loginAdmin", securityDetails.getUserEntity());
        return "admin/board/detail";
    }

    @PostMapping("/admin/board/detail/comment")
    public ResponseEntity comment(@ModelAttribute CommentDTO commentDTO){
        System.out.println("commentDTO" + commentDTO);
        Long saveResult = commentService.saveComment(commentDTO);
        if(saveResult != null){
            List<CommentDTO> commentDTOList = commentService.findCommentAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    // 공지 생성
    @GetMapping("/admin/noti/save")
    public String saveNotiForm() {
        return "admin/noti/save";
    }

    @PostMapping("/admin/noti/save")
    public String saveNoti(@ModelAttribute BoardDTO boardDTO, @AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
        boardService.saveNoti(boardDTO, securityDetails.getUserEntity());
        return "redirect:/admin/noti";
    }

    // 공지 조회
    @GetMapping("/admin/noti")
    public String findAllnoti(Model model, @AuthenticationPrincipal SecurityDetails securityDetails) {
        List<BoardDTO> boardDTOList = boardService.findNoti();
        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("boardListCount", boardDTOList.size());
        model.addAttribute("adminName", securityDetails.getUserEntity().getUserEmail());
        return "admin/noti/noti";
    }

    // 공지 상세 조회
    @GetMapping("/admin/noti/detail/{id}")
    public String findDetailNoti(@PathVariable Long id, @AuthenticationPrincipal SecurityDetails securityDetails, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "admin/noti/detail";
    }

    // 공지 수정
    @GetMapping("/admin/noti/edit/{id}")
    public String editNotiForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("editBoard", boardDTO);
        return "/admin/noti/editBoard";
    }

    @PostMapping("/admin/noti/edit")
    public String editNoti(@AuthenticationPrincipal SecurityDetails securityDetails, @ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.editNoti(boardDTO, securityDetails.getUserEntity());
        model.addAttribute("board", board);
        return "redirect:/admin/noti";
    }

    // 공지 삭제
    @GetMapping("/admin/noti/delete/{id}")
    public String deleteNoti(@PathVariable Long id) throws IOException {
        boardService.deleteById(id);
        return "redirect:/admin/noti";
    }

}
