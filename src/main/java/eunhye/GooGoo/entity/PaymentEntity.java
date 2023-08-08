package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private int year;
    private int month;
    private int date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private UserEntity userEntity;

    public static PaymentEntity toSaveEntity(PaymentDTO paymentDTO){
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.name = paymentDTO.getName();
        paymentEntity.price = paymentDTO.getPrice();
        paymentEntity.year = paymentDTO.getYear();
        paymentEntity.month = paymentDTO.getMonth();
        paymentEntity.date = paymentDTO.getDate();
        return paymentEntity;
    }
}
