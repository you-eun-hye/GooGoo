package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.BoardDTO;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "board")
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boardWriter;

    @Column(nullable = false)
    private String boardTitle;

    @Column(length = 500, nullable = false)
    private String boardContent;

    @Column
    private int fileAttached;

    public static BoardEntity toSaveEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.boardWriter = boardDTO.getBoardWriter();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContent = boardDTO.getBoardContent();
        boardEntity.fileAttached = 0;
        return boardEntity;
    }

    public static BoardEntity toEditEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.id = boardDTO.getId();
        boardEntity.boardWriter = boardDTO.getBoardWriter();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContent = boardDTO.getBoardContent();
        return boardEntity;
    }
}
