package eunhye.GooGoo.service.user;

import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.entity.PaymentEntity;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
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
    public List<PaymentDTO> findTop3(UserEntity userEntity){
        List<PaymentEntity> paymentEntityList = paymentRepository.findTOP3List(userEntity.getId());
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        for(PaymentEntity paymentEntity : paymentEntityList){
            paymentDTOList.add(PaymentDTO.toPaymentDTO(paymentEntity));
        }
        return paymentDTOList;
    }


    // 전체 조회
    public List<PaymentDTO> findAll(Long id){
        List<PaymentEntity> paymentEntityList = paymentRepository.findAll();
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        for(PaymentEntity paymentEntity : paymentEntityList){
            if(paymentEntity.getUserEntity().getId() == id){
                paymentDTOList.add(PaymentDTO.toPaymentDTO(paymentEntity));
            }
        }
        return paymentDTOList;
    }

    // 페이징 조회
    public Page<PaymentEntity> paging(Pageable pageable, UserEntity userEntity){
        Page<PaymentEntity> paymentEntityList = paymentRepository.pagingList(userEntity.getId(), pageable);
        return paymentEntityList;
    }


    // 결제 내역 삭제
    public void deleteById(Long id){
        paymentRepository.deleteById(id);
    }
}
