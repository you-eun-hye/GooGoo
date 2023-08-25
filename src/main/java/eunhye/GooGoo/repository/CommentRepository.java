package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityOrderByIdAsc(BoardEntity boardEntity);
}
