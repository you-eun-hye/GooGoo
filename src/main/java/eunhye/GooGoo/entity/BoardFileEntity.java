package eunhye.GooGoo.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
@Table(name = "BOARD_FILE")
public class BoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column
    private String originalFileName; // 사진명

    @Column
    private String storedFileName; // 저장 시 사진명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static BoardFileEntity toBoardFileEntity(BoardEntity boardEntity, String originalFileName, String storedFileName){
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.originalFileName = originalFileName;
        boardFileEntity.storedFileName = storedFileName;
        boardFileEntity.boardEntity = boardEntity;
        return boardFileEntity;
    }
}
