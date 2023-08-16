package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.PaymentEntity;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long id;
    private String imgSrc;
    private String name;
    private int price;
    private int year;
    private int month;
    private int date;
    private UserEntity userEntity;

    public static PaymentDTO toPaymentDTO(PaymentEntity paymentEntity){
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(paymentEntity.getId());
        paymentDTO.setImgSrc(paymentEntity.getImgSrc());
        paymentDTO.setName(paymentEntity.getName());
        paymentDTO.setPrice(paymentEntity.getPrice());
        paymentDTO.setYear(paymentEntity.getYear());
        paymentDTO.setMonth(paymentEntity.getMonth());
        paymentDTO.setDate(paymentEntity.getDate());
        return paymentDTO;
    }
}
