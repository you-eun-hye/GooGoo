package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.CommentDTO;
import eunhye.GooGoo.service.BoardService;
import eunhye.GooGoo.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    /*
    * 사용자
    */

    // 문의글 작성
    @Operation(summary = "Q&A Save Form", description = "문의글 작성 페이지")
    @GetMapping("/board/save")
    public String saveQuestionForm(){
        return "user/board/save";
    }

    @Operation(summary = "Q&A Save", description = "문의글 작성")
    @ResponseBody
    @PostMapping("/api/board/save")
    public ResponseEntity saveQuestion(@Parameter(description = "문의글 정보") @ModelAttribute BoardDTO boardDTO,
                               @Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
        boardService.save(boardDTO, securityDetails.getUserEntity());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "board", description = "내가 쓴 문의글 5개씩 최신순 페이징 조회")
    @GetMapping("/board")
    public String findMyQuestion(@Parameter(description = "5개씩 최신순 본인인 쓴 문의글 페이징") @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                 @Parameter(description = "문의글, 현재 페이지, 시작 페이지, 마지막 페이지 담을 model")  Model model,
                                 @Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails){
        Page<BoardDTO> list = boardService.paging(pageable, securityDetails.getUserEntity());

        int nowPage = list.getPageable().getPageNumber() +1;
        int startPage = Math.max(nowPage -4, 1);
        int endPage = Math.min(nowPage +5, list.getTotalPages());

        model.addAttribute("boardList", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "user/board/board";
    }

    @Operation(summary = "concreate board", description = "문의글 상세 조회 페이지")
    @GetMapping("/board/{id}")
    public String findDetailQuestion(@Parameter(description = "상세 조회할 문의글 고유 번호") @PathVariable UUID id,
                                     @Parameter(description = "상세 조회할 문의글과 댓글을 담을 model") Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        List<CommentDTO> commentDTOList = commentService.findCommentAll(id);
        model.addAttribute("commentList", commentDTOList);
        return "user/board/detail";
    }

    @Operation(summary = "edit board", description = "문의글 수정 페이지")
    @GetMapping("/board/edit/{id}")
    public String editQuestionForm(@Parameter(description = "수정할 문의글 고유 번호") @PathVariable UUID id,
                                   @Parameter(description = "수정할 문의글 담을 model") Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("editBoard", boardDTO);
        return "user/board/editBoard";
    }

    @Operation(summary = "edit board api", description = "문의글 수정 api")
    @ResponseBody
    @PatchMapping("/api/board/edit")
    public ResponseEntity editQuestion(@Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails,
                               @Parameter(description = "수정할 문의글 DTO") BoardDTO boardDTO,
                               @Parameter(description = "수정한 문의글 담을 model") Model model){
        BoardDTO board = boardService.edit(boardDTO, securityDetails.getUserEntity());
        model.addAttribute("board", board);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "delete board api", description = "문의글 삭제 api")
    @ResponseBody
    @DeleteMapping("/api/board/delete/{id}")
    public ResponseEntity deleteQuestion(@Parameter(description = "수정할 문의글 고유번호") @PathVariable UUID id) throws IOException {
        boardService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    * 관리자
    */

    @Operation(summary = "boardList adminVer", description = "문의글 조회 페이지 관리자ver")
    @GetMapping("/admin/board")
    public String findAllQuestion(@Parameter(description = "문의글 전체 리스트, 미답변 문의글 갯수, 로그인 중인 관리자명 담을 model") Model model,
                                  @Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails) {
        List<BoardDTO> boardDTOList = boardService.findBoard();
        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("waitComment", boardService.waitComment());
        model.addAttribute("adminName", securityDetails.getUserEntity().getUserEmail());
        return "admin/board/board";
    }

    @Operation(summary = "boardList detail adminVer", description = "문의글 상세 조회 페이지 관리자ver")
    @GetMapping("/admin/board/detail/{id}")
    public String commentForm(@Parameter(description = "상세조회할 문의글 고유번호") @PathVariable UUID id,
                              @Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails,
                              @Parameter(description = "boardDTO, commentDTO, 로그인 중인 관리자 정보 담을 model") Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        List<CommentDTO> commentDTOList = commentService.findCommentAll(id);
        model.addAttribute("commentList", commentDTOList);

        model.addAttribute("loginAdmin", securityDetails.getUserEntity());
        return "admin/board/detail";
    }

    @Operation(summary = "comment api", description = "문의글 답변 api")
    @PostMapping("/api/admin/board/detail/comment")
    public ResponseEntity comment(@Parameter(description = "댓글 DTO")  CommentDTO commentDTO){
        System.out.println("commentDTO" + commentDTO);
        UUID saveResult = commentService.saveComment(commentDTO);
        if(saveResult != null){
            List<CommentDTO> commentDTOList = commentService.findCommentAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "noti save form", description = "공지 생성 페이지")
    @GetMapping("/admin/noti/save")
    public String saveNotiForm() {
        return "admin/noti/save";
    }

    @Operation(summary = "noti save api", description = "공지 생성 api")
    @ResponseBody
    @PostMapping("/api/admin/noti/save")
    public ResponseEntity saveNoti(@Parameter(description = "생성한 공지 DTO") BoardDTO boardDTO,
                                   @Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails) throws IOException {
        boardService.saveNoti(boardDTO, securityDetails.getUserEntity());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "noti", description = "공지 조회 페이지")
    @GetMapping("/admin/noti")
    public String findAllnoti(@Parameter(description = "전체 공지글, 공지글 전체 갯수, 관리자명 담을 model") Model model,
                              @Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails) {
        List<BoardDTO> boardDTOList = boardService.findNoti();
        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("boardListCount", boardDTOList.size());
        model.addAttribute("adminName", securityDetails.getUserEntity().getUserEmail());
        return "admin/noti/noti";
    }

    @Operation(summary = "noti detail", description = "공지 상세 조회 페이지")
    @GetMapping("/admin/noti/detail/{id}")
    public String findDetailNoti(@Parameter(description = "상세 조회할 공지글 고유 번호") @PathVariable UUID id,
                                 @Parameter(description = "공지글 담을 model") Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "admin/noti/detail";
    }

    @Operation(summary = "noti edit form", description = "공지 수정 페이지")
    @GetMapping("/admin/noti/edit/{id}")
    public String editNotiForm(@Parameter(description = "수정할 공지글 고유 번호") @PathVariable UUID id,
                               @Parameter(description = "수정할 공지글 담을 model") Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("editBoard", boardDTO);
        return "/admin/noti/editBoard";
    }

    @Operation(summary = "noti edit api", description = "공지 수정 api")
    @ResponseBody
    @PatchMapping("/api/admin/noti/edit")
    public ResponseEntity editNoti(@Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails,
                                   @Parameter(description = "수정한 공지글 DTO") BoardDTO boardDTO,
                                   @Parameter(description = "수정한 공지글 담을 model") Model model){
        BoardDTO board = boardService.editNoti(boardDTO, securityDetails.getUserEntity());
        model.addAttribute("board", board);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "noti delete api", description = "공지 삭제 api")
    @ResponseBody
    @DeleteMapping("/api/admin/noti/delete/{id}")
    public ResponseEntity deleteNoti(@Parameter(description = "삭제할 공지글 고유 번호") @PathVariable UUID id) throws IOException {
        boardService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
