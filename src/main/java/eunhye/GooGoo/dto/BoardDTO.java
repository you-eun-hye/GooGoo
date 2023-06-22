package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private List<MultipartFile> boardFile; // save.html -> controller 파일 담는 용도
    private List<String> originalFileName; // 원본 파일 이름
    private List<String> storedFileName; // 서버 저장용 파일 이름
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

        if(boardEntity.getFileAttached() == 0){ // 파일 없을 때
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0
        }else{ // 파일 있을 때
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1

            // 파일 이름 가져오기
            for(BoardFileEntity boardFileEntity: boardEntity.getBoardFileEntityList()){
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }

            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);
        }

        return boardDTO;
    }
}
