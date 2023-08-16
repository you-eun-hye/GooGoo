package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.PaymentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgSrc;
    private String name;
    private int price;
    private int year;
    private int month;
    private int date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public static PaymentEntity toSaveEntity(PaymentDTO paymentDTO){
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.imgSrc = paymentDTO.getImgSrc();
        paymentEntity.name = paymentDTO.getName();
        paymentEntity.price = paymentDTO.getPrice();
        paymentEntity.year = paymentDTO.getYear();
        paymentEntity.month = paymentDTO.getMonth();
        paymentEntity.date = paymentDTO.getDate();
        return paymentEntity;
    }
}
