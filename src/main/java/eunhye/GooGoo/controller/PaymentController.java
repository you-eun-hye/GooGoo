package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    // 조회
//    @GetMapping("/calendar")
//    public String calendarForm(){
//        return "payment/calendar";
//    }

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray findAll(Model model, @AuthenticationPrincipal SecurityDetails securityDetails){
        List<PaymentDTO> paymentDTOList = paymentService.findAll(securityDetails.getUserEntity());
        model.addAttribute("paymentList", paymentDTOList);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        HashMap<String, Object> paymentHash = new HashMap<>();

        for(int i = 0; i < paymentDTOList.size(); i++){
            paymentHash.put("title", paymentDTOList.get(i).getName());
            paymentHash.put("start", paymentDTOList.get(i).getYear() + "-" + paymentDTOList.get(i).getMonth() + "-" + paymentDTOList.get(i).getDate());

            jsonObject = new JSONObject(paymentHash);
            jsonArray.add(jsonObject);
            System.out.println(jsonArray);
        }
        return jsonArray;
    }


    // 결제 금액 TOP3 겸 Main 페이지
    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal SecurityDetails securityDetails) {
        List<PaymentDTO> paymentDTOList = paymentService.findTop3(securityDetails.getUserEntity());
        model.addAttribute("top3", paymentDTOList);
        model.addAttribute("sumPrice", paymentService.sumPrice());
        return "payment/home";
    }
}
