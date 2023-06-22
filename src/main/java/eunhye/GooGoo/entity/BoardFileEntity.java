package eunhye.GooGoo.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "board_file_table")
public class BoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

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
