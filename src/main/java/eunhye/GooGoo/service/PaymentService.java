package eunhye.GooGoo.service;

import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.entity.PaymentEntity;
import eunhye.GooGoo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;


    // 누적 금액
    public Integer sumPrice() {
        return paymentRepository.sumPrice();
    }


    // 결제 금액 TOP3 조회
    public List<PaymentDTO> findTop3(){
        List<PaymentEntity> paymentEntityList = paymentRepository.findTop3ByOrderByPriceDesc();
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        for(PaymentEntity paymentEntity : paymentEntityList){
            paymentDTOList.add(PaymentDTO.toPaymentDTO(paymentEntity));
        }
        return paymentDTOList;
    }


    // 전체 조회
    public List<PaymentDTO> findAll(){
        List<PaymentEntity> paymentEntityList = paymentRepository.findAll();
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        for(PaymentEntity paymentEntity : paymentEntityList){
            paymentDTOList.add(PaymentDTO.toPaymentDTO(paymentEntity));
        }
        return paymentDTOList;
    }
}
