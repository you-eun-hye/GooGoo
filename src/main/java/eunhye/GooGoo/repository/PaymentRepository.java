package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findTop3ByOrderByPriceDesc();

    @Query("SELECT SUM(p.price) FROM PaymentEntity p")
    Integer sumPrice();
}
