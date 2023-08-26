package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.BoardFileEntity;
import eunhye.GooGoo.entity.UserEntity;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime boardCreatedTime;
    private UserEntity userEntity;
    private int noti;
    private int commentAttached;

    private MultipartFile boardFile; // save.html -> controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)


    public static BoardDTO toBoardDTO(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContent(boardEntity.getBoardContent());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setUserEntity(boardEntity.getUserEntity());
        boardDTO.setNoti(boardEntity.getNoti());
        boardDTO.setNoti(boardEntity.getCommentAttached());
        if(boardEntity.getFileAttached() == 0){
            boardDTO.setFileAttached(boardEntity.getFileAttached());
        }else{
            boardDTO.setFileAttached(boardEntity.getFileAttached());
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());

        }
        return boardDTO;
    }

    public Page<BoardDTO> toDTOList(Page<BoardEntity> boardEntityList) {
        Page<BoardDTO> boardDTOList = boardEntityList.map(m -> BoardDTO.builder()
                .id(m.getId())
                .boardTitle(m.getBoardTitle())
                .boardContent(m.getBoardContent())
                .boardCreatedTime(m.getCreatedTime())
                .userEntity(m.getUserEntity())
                .noti(m.getNoti())
                .commentAttached(m.getCommentAttached())
                .fileAttached(m.getFileAttached())
                .build());
        return boardDTOList;
    }
}
