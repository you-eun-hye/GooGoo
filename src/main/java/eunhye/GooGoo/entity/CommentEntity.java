package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "COMMENT")
public class CommentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, UserEntity userEntity, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.id = commentDTO.getId();
        commentEntity.userEntity = userEntity;
        commentEntity.commentContents = commentDTO.getCommentContents();
        commentEntity.boardEntity = boardEntity;
        return commentEntity;
    }
}
