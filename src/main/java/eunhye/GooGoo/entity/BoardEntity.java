package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.BoardDTO;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "board")
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boardTitle;

    @Column(length = 500, nullable = false)
    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column
    private int fileAttached;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    public static BoardEntity toSaveEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContent = boardDTO.getBoardContent();
        boardEntity.userEntity = boardDTO.getUserEntity();
        boardEntity.fileAttached = 0;
        return boardEntity;
    }

    public static BoardEntity toEditEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.id = boardDTO.getId();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContent = boardDTO.getBoardContent();
        boardEntity.userEntity = boardDTO.getUserEntity();
        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContent = boardDTO.getBoardContent();
        boardEntity.userEntity = boardDTO.getUserEntity();
        boardEntity.fileAttached = 1;
        return boardEntity;
    }
}
