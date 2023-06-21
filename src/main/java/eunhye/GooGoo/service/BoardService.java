package eunhye.GooGoo.service;

import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if(boardDTO.getBoardFile().isEmpty()){
            // 첨부 파일 없음
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            boardRepository.save(boardEntity);
        }else{
            // 첨부 파일 있음
            /*
            * 6. board_table에 해당 데이터 save 처리
            * 7. board_file_table에 해당 데이터 save 처리
            */
            MultipartFile boardFile = boardDTO.getBoardFile(); // 1. DTO에 담긴 파일을 꺼냄
            String originalFilename = boardFile.getOriginalFilename(); // 2. 파일의 이름 가져옴
            String storeFileName = System.currentTimeMillis() + "_" + originalFilename; // 3. 서버 저장용 이름 만듦
            String savePath = "D:/GooGoo_img/" + storeFileName; // 4. 저장 경로 설정
            boardFile.transferTo(new File(savePath)); // 5. 해당 경로에 파일 저장


        }
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

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

    public BoardDTO edit(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toEditEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

//    public Page<BoardDTO> paging(Pageable pageable) {
//        int page = pageable.getPageNumber() -1;
//        int pageLimit = 5;
//        Page<BoardEntity> boardEntities =
//                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
//
//        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getCreatedTime()));
//        return boardDTOS;
//    }
}
