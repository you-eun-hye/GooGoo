package eunhye.GooGoo.service;

import eunhye.GooGoo.dto.BoardDTO;
import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDTO boardDTO, UserDTO userDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, userDTO);
        boardRepository.save(boardEntity);
    }
}
