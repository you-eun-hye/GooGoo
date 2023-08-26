package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityOrderByIdAsc(BoardEntity boardEntity);

    @Query("SELECT DISTINCT c.boardEntity FROM CommentEntity c")
    List<BoardEntity> findBoardEntity();
}
