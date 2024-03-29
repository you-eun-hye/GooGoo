package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "BOARD")
@NoArgsConstructor
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private String boardTitle;

    @Column(length = 500, nullable = false)
    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column
    private int fileAttached; // 사진 첨부 유무

    @Column
    private int noti; // 공지 유무

    @Column
    private int commentAttached; // 답글 유무

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    // 문의글 + 사진 미첨부
    public static BoardEntity toSaveEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContent = boardDTO.getBoardContent();
        boardEntity.userEntity = boardDTO.getUserEntity();
        boardEntity.noti = 0;
        boardEntity.fileAttached = 0;
        boardEntity.commentAttached = 0;
        return boardEntity;
    }

    // 문의글 + 사진 첨부
    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContent = boardDTO.getBoardContent();
        boardEntity.userEntity = boardDTO.getUserEntity();
        boardEntity.noti = 0;
        boardEntity.fileAttached = 1;
        boardEntity.commentAttached = 0;
        return boardEntity;
    }

    // 공지글 + 사진 미첨부
    public static BoardEntity toSaveFNoti(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContent = boardDTO.getBoardContent();
        boardEntity.userEntity = boardDTO.getUserEntity();
        boardEntity.noti = 1;
        boardEntity.fileAttached = 0;
        boardEntity.commentAttached = 0;
        return boardEntity;
    }

    // 공지글 + 사진 첨부
    public static BoardEntity toSaveNotiFile(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContent = boardDTO.getBoardContent();
        boardEntity.userEntity = boardDTO.getUserEntity();
        boardEntity.noti = 1;
        boardEntity.fileAttached = 1;
        boardEntity.commentAttached = 0;
        return boardEntity;
    }

    // 문의글 혹은 공지글 수정
    public static BoardEntity toEditEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.id = boardDTO.getId();
        boardEntity.boardTitle = boardDTO.getBoardTitle();
        boardEntity.boardContent = boardDTO.getBoardContent();
        boardEntity.userEntity = boardDTO.getUserEntity();
        boardEntity.fileAttached = boardDTO.getFileAttached();
        boardEntity.commentAttached = boardDTO.getCommentAttached();
        boardEntity.noti = boardDTO.getNoti();
        return boardEntity;
    }
}
