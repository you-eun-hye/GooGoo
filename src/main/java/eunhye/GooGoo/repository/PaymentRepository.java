package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    @Query("SELECT p FROM PaymentEntity p where p.userEntity.id = ?1 order by p.price DESC LIMIT 3")
    List<PaymentEntity> findTOP3List(Long id);

    @Query("SELECT SUM(p.price) FROM PaymentEntity p")
    Integer sumPrice();

    Long countBy();
}
