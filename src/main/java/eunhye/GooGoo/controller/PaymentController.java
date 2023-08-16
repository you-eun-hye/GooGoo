package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.FlaskDTO;
import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.repository.PaymentRepository;
import eunhye.GooGoo.service.PaymentService;
import eunhye.GooGoo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final UserService userService;


    // 조회
    @GetMapping("/calendar")
    public String findAll(Model model){
        List<PaymentDTO> paymentDTOList = paymentService.findAll();
        model.addAttribute("paymentList", paymentDTOList);
        return "payment/calendar";
    }

    // 캘린더 형식 조회
    //@GetMapping("/calendar")
    //public String calendarForm(){
    //    return "payment/calendar";
    //}

    // 결제 금액 TOP3 겸 Main 페이지
    @GetMapping("/home")
    public String home(Model model) {
        List<PaymentDTO> paymentDTOList = paymentService.findTop3();
        model.addAttribute("top3", paymentDTOList);
        model.addAttribute("sumPrice", paymentService.sumPrice());
        return "payment/home";
    }
}
