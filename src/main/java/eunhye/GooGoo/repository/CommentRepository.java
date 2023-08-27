package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // 게시물로 댓글 오름차순 조회
    List<CommentEntity> findAllByBoardEntityOrderByIdAsc(BoardEntity boardEntity);
}
