package eunhye.GooGoo.service;

import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.BoardFileEntity;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.repository.BoardFileRepository;
import eunhye.GooGoo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    // 게시물 저장
    public void save(BoardDTO boardDTO, UserEntity userEntity) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        MultipartFile boardFile = boardDTO.getBoardFile(); // DTO에 담긴 파일을 꺼냄
        String originalFilename = boardFile.getOriginalFilename(); // 파일의 이름 가져옴

        if(originalFilename.equals("")){
            boardDTO.setUserEntity(userEntity);
            BoardEntity boardEntityNot = BoardEntity.toSaveEntity(boardDTO);
            boardRepository.save(boardEntityNot);
        }
        else{
            boardDTO.setUserEntity(userEntity);
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            Long saveId = boardRepository.save(boardEntity).getId(); // board_table에 해당 데이터 save 처리
            BoardEntity board = boardRepository.findById(saveId).get(); // 부모 객체를 가져옴

            String storeFileName = System.currentTimeMillis() + "_" + originalFilename; // 서버 저장용 이름 만듦
            String savePath = "D:/GooGoo_img/" + storeFileName; // 저장 경로 설정
            boardFile.transferTo(new File(savePath)); // 해당 경로에 파일 저장

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storeFileName);
            boardFileRepository.save(boardFileEntity); // board_file_table에 해당 데이터 save 처리
        }
    }


    // 공지글 저장
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


    // 페이징 처리
    public Page<BoardDTO> paging(Pageable pageable, UserEntity userEntity){
        Page<BoardEntity> boardEntityList = boardRepository.pagingList(userEntity.getId(), pageable);
        Page<BoardDTO> boardDTOList = new BoardDTO().toDTOList(boardEntityList);
        return boardDTOList;
    }


    // id로 게시물 조회
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        }else{
            return null;
        }
    }


    // 문의글 전체 조회
    public List<BoardDTO> findBoard() {
        List<BoardEntity> boardEntityList = boardRepository.boardEntityList();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }


    // 내가 쓴 문의글 조회
    public List<BoardDTO> findUserBoard(Long id) {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity: boardEntityList){
            if(boardEntity.getUserEntity().getId() == id){
                boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
            }
        }
        return boardDTOList;
    }


    // 공지글 조회
    public List<BoardDTO> findNoti() {
        List<BoardEntity> boardEntityList = boardRepository.boardNotiList();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }


    // Q&A 수정
    public BoardDTO edit(BoardDTO boardDTO, UserEntity userEntity) {
        boardDTO.setUserEntity(userEntity);
        BoardEntity boardEntity = BoardEntity.toEditEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }


    // 공지 수정
    public BoardDTO editNoti(BoardDTO boardDTO, UserEntity userEntity) {
        boardDTO.setUserEntity(userEntity);
        boardDTO.setNoti(1);
        BoardEntity boardEntity = BoardEntity.toEditEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }


    // 답변 미완료 문의글 갯수
    public Integer waitComment(){
        return boardRepository.waitAnswer();
    }


    // 게시물 삭제
    public void deleteById(Long id) throws IOException {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        BoardDTO boardDTO = BoardDTO.toBoardDTO(optionalBoardEntity.get());
        File boardFile = new File("D:/GooGoo_img/" + boardDTO.getStoredFileName());
        boardFile.delete();
        boardRepository.deleteById(id);
    }


}
