package eunhye.GooGoo.service.admin;

import eunhye.GooGoo.dto.CommentDTO;
import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.CommentEntity;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import eunhye.GooGoo.repository.BoardRepository;
import eunhye.GooGoo.repository.CommentRepository;
import eunhye.GooGoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 회원 조회
    public List<UserDTO> findUserAll(){
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList){
            if(userEntity.getAuthority() == UserRole.USER){
                userDTOList.add(UserDTO.toUserDTO(userEntity));
            }
        }
        return userDTOList;
    }

    // 관리자 조회
    public List<UserDTO> findAdminAll(){
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList){
            if(userEntity.getAuthority() == UserRole.ADMIN){
                userDTOList.add(UserDTO.toAdminDTO(userEntity));
            }
        }
        return userDTOList;
    }

    // 회원 수
    public long countUser(){
        return userRepository.countUser();
    }

    // 관리자 수
    public long countAdmin(){
        return userRepository.countUAdmin();
    }

    // 댓글 작성
    public Long save(CommentDTO commentDTO){
        UserEntity userEntity = userRepository.findByUserEmail(commentDTO.getCommentWriter());
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (userEntity!=null && optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, userEntity, boardEntity);
            return commentRepository.save(commentEntity).getId();
        }else{
            return null;
        }
    }

    public List<CommentDTO> findCommentAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdAsc(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntityList){
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
