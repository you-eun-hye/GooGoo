package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // 페이징 조회
    @Query("SELECT b FROM BoardEntity b WHERE b.userEntity.id = ?1 OR b.noti = 1 ORDER BY b.noti DESC")
    Page<BoardEntity> pagingList(Long id, Pageable pageable);

    // 문의글 조회
    @Query("SELECT b FROM BoardEntity b WHERE b.noti = 0")
    List<BoardEntity> boardEntityList();

    // 공지글 조회
    @Query("SELECT b FROM BoardEntity b WHERE b.noti = 1")
    List<BoardEntity> boardNotiList();
}
