package eunhye.GooGoo.controller;

import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 조회
    @GetMapping("/buyList")
    public String findAll(Model model){
        List<PaymentDTO> paymentDTOList = paymentService.findAll();
        model.addAttribute("paymentList", paymentDTOList);
        return "payment/buyList";
    }

    // 캘린더 형식 조회
    @GetMapping("/calendar")
    public String calendarForm(){
        return "payment/calendar";
    }

    // 결제 금액 TOP3 겸 Main 페이지
    @GetMapping("/home")
    public String home(Model model) {
        List<PaymentDTO> paymentDTOList = paymentService.findTop3();
        model.addAttribute("top3", paymentDTOList);

        model.addAttribute("sumPrice", paymentService.sumPrice());
        return "payment/home";
    }
}
