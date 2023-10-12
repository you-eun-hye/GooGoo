package eunhye.GooGoo.service;

import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.CommentDTO;
import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.CommentEntity;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.repository.BoardRepository;
import eunhye.GooGoo.repository.CommentRepository;
import eunhye.GooGoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성
    public UUID saveComment(CommentDTO commentDTO){
        UserEntity userEntity = userRepository.findByUserEmail(commentDTO.getCommentWriter());
        if (userEntity!=null){
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardRepository.findById(commentDTO.getBoardId()));
            boardDTO.setCommentAttached(1);
            BoardEntity boardEntity = BoardEntity.toEditEntity(boardDTO);
            boardRepository.save(boardEntity);
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, userEntity, boardEntity);
            return commentRepository.save(commentEntity).getId();
        }else{
            return null;
        }
    }


    // 댓글 조회
    public List<CommentDTO> findCommentAll(UUID boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId);
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdAsc(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntityList){
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

}
