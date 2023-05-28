package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board")
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boardTitle;

    @Column(length = 500, nullable = false)
    private String boardContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public static BoardEntity toSaveEntity(BoardDTO boardDTO, UserDTO userDTO){
        BoardEntity boardEntity = new BoardEntity();
        UserEntity userEntity = new UserEntity();
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContent(boardDTO.getBoardContent());
        boardEntity.setUserEntity(userDTO.getId());
        return boardEntity;
    }
}
