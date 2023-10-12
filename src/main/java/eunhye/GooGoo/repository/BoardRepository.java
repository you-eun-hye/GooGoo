package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // 고유번호로 문의글 정보 조회
    BoardEntity findById(UUID boardId);

    // 고유번호로 문의글 삭제
    BoardEntity deleteById(UUID boardId);

    // 페이징 조회
    @Query("SELECT b FROM BoardEntity b WHERE b.userEntity.id = ?1 OR b.noti = 1 ORDER BY b.noti DESC")
    Page<BoardEntity> pagingList(UUID id, Pageable pageable);

    // 문의글 조회
    @Query("SELECT b FROM BoardEntity b WHERE b.noti = 0")
    List<BoardEntity> boardEntityList();

    // 공지글 조회
    @Query("SELECT b FROM BoardEntity b WHERE b.noti = 1")
    List<BoardEntity> boardNotiList();

    // 미답변 문의글 갯수 조회
    @Query("SELECT COUNT(b) FROM BoardEntity b WHERE b.commentAttached = 0 AND b.noti = 0")
    Integer waitAnswer();
}
