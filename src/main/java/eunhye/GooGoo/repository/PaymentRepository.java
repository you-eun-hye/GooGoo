package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    // 결제 금액이 큰 순으로 3개 조회
    @Query("SELECT p FROM PaymentEntity p where p.userEntity.id = ?1 order by p.price DESC LIMIT 3")
    List<PaymentEntity> findTOP3List(Long id);

    // 결제 총 액
    @Query("SELECT SUM(p.price) FROM PaymentEntity p")
    Integer sumPrice();

    // 유저 id에 따른 결제 내역 조회
    @Query("SELECT p FROM PaymentEntity p where p.userEntity.id = ?1")
    Page<PaymentEntity> pagingList(Long id, Pageable pageable);
}
