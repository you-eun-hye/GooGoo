package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.PaymentEntity;
import eunhye.GooGoo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    @Query("SELECT p FROM PaymentEntity p where p.userEntity.id = ?1 order by p.price DESC LIMIT 3")
    List<PaymentEntity> findTOP3List(Long id);

    @Query("SELECT SUM(p.price) FROM PaymentEntity p")
    Integer sumPrice();

    @Query("SELECT p FROM PaymentEntity p where p.userEntity.id = ?1")
    Page<PaymentEntity> pagingList(Long id, Pageable pageable);
}
