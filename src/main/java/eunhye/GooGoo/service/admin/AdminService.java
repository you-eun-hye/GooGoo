package eunhye.GooGoo.service.admin;

import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.CommentDTO;
import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.*;
import eunhye.GooGoo.repository.BoardFileRepository;
import eunhye.GooGoo.repository.BoardRepository;
import eunhye.GooGoo.repository.CommentRepository;
import eunhye.GooGoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
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
    public Long saveComment(CommentDTO commentDTO){
        UserEntity userEntity = userRepository.findByUserEmail(commentDTO.getCommentWriter());
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (userEntity!=null && optionalBoardEntity.isPresent()){
            BoardDTO boardDTO = BoardDTO.toBoardDTO(optionalBoardEntity.get());
            boardDTO.setCommentAttached(1);
            BoardEntity boardEntity = BoardEntity.toEditEntity(boardDTO);
            boardRepository.save(boardEntity);
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

    // 답변 미완료 문의글 갯수
    public Integer waitComment(){
        return boardRepository.waitAnswer();
    }

    // 공지글 작성
    public void saveNoti(BoardDTO boardDTO, UserEntity userEntity) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        MultipartFile boardFile = boardDTO.getBoardFile(); // DTO에 담긴 파일을 꺼냄
        String originalFilename = boardFile.getOriginalFilename(); // 파일의 이름 가져옴

        if(originalFilename.equals("")){
            boardDTO.setUserEntity(userEntity);
            BoardEntity boardEntityNot = BoardEntity.toSaveFNoti(boardDTO);
            boardRepository.save(boardEntityNot);
        }
        else{
            boardDTO.setUserEntity(userEntity);
            BoardEntity boardEntity = BoardEntity.toSaveNotiFile(boardDTO);
            Long saveId = boardRepository.save(boardEntity).getId(); // board_table에 해당 데이터 save 처리
            BoardEntity board = boardRepository.findById(saveId).get(); // 부모 객체를 가져옴

            String storeFileName = System.currentTimeMillis() + "_" + originalFilename; // 서버 저장용 이름 만듦
            String savePath = "D:/GooGoo_img/" + storeFileName; // 저장 경로 설정
            boardFile.transferTo(new File(savePath)); // 해당 경로에 파일 저장

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storeFileName);
            boardFileRepository.save(boardFileEntity); // board_file_table에 해당 데이터 save 처리
        }
    }
}
