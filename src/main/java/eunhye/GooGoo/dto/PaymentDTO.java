package eunhye.GooGoo.dto;

import eunhye.GooGoo.entity.PaymentEntity;
import eunhye.GooGoo.entity.UserEntity;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private UUID id;
    private String imgSrc; // 앱 이미지 SRC
    private String name; // 앱 이름
    private int price; // 결제 금액
    private int year; // 결제 연도
    private int month; // 결제 달
    private int date; // 결제 일
    private UserEntity userEntity;

    // Entity -> DTO
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
