package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.BoardEntity;
import eunhye.GooGoo.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Query("SELECT b FROM BoardEntity b where b.userEntity.id = ?1")
    Page<BoardEntity> pagingList(Long id, Pageable pageable);

}
