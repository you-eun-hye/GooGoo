package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    // 조회
    @GetMapping("/calendar")
    public String calendarForm(@PageableDefault(page = 1) Pageable pageable, @AuthenticationPrincipal SecurityDetails securityDetails, Model model){
        List<PaymentDTO> paymentDTOList = paymentService.findAll(securityDetails.getUserEntity());
        model.addAttribute("paymentList", paymentDTOList);
        return "payment/calendar";
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
        return "payment/home";
    }
}
