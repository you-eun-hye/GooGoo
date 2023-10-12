package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CommentDTO {
    private UUID id;
    private String commentWriter;
    private String commentContents;
    private UUID boardId;
    private LocalDateTime commentCreatedTime;

    // Entity -> DTO
    public static CommentDTO toCommentDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getUserEntity().getUserEmail());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());
        commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());
        return commentDTO;
    }
}
