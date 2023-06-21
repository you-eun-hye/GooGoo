package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime boardCreatedTime;

    private MultipartFile boardFile; // save.html -> controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

//    public BoardDTO(Long id, String boardWriter, String boardTitle, LocalDateTime boardCreatedTime) {
//        this.id = id;
//        this.boardWriter = boardWriter;
//        this.boardTitle = boardTitle;
//        this.boardCreatedTime = boardCreatedTime;
//    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContent(boardEntity.getBoardContent());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        return boardDTO;
    }
}
