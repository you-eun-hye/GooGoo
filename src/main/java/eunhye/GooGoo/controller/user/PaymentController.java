package eunhye.GooGoo.controller.user;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.entity.PaymentEntity;
import eunhye.GooGoo.service.user.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    // 조회
    @GetMapping("/calendar")
    public String calendarForm(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @AuthenticationPrincipal SecurityDetails securityDetails, Model model){
        Page<PaymentEntity> list = paymentService.paging(pageable, securityDetails.getUserEntity());

        int nowPage = list.getPageable().getPageNumber() +1 ;
        int startPage = Math.max(nowPage -4, 1);
        int endPage = Math.min(nowPage +5, list.getTotalPages());

        model.addAttribute("paymentList", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "user/payment/calendar";
    }

//    @GetMapping("/calendar")
//    @ResponseBody
//    public List<Map<String, Object>> findAll(@AuthenticationPrincipal SecurityDetails securityDetails){
//        List<PaymentDTO> paymentDTOList = paymentService.findAll(securityDetails.getUserEntity());
//
//        Map<String, Object> event = new HashMap<String, Object>();
//        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
//
//        event.put("start", paymentDTOList.get(0).getYear() + "-" + paymentDTOList.get(0).getMonth() + "-" + paymentDTOList.get(0).getDate());
//        event.put("title", paymentDTOList.get(0).getName());
//        eventList.add(event);
//        return eventList;
//    }


    // 결제 금액 TOP3 겸 Main 페이지
    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal SecurityDetails securityDetails) {
        List<PaymentDTO> paymentDTOList = paymentService.findTop3(securityDetails.getUserEntity());
        model.addAttribute("top3", paymentDTOList);
        model.addAttribute("sumPrice", paymentService.sumPrice());
        return "user/payment/home";
    }
}
