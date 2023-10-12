package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.PaymentDTO;
import eunhye.GooGoo.entity.PaymentEntity;
import eunhye.GooGoo.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "buyList", description = "결제 내역 5개씩 최신순 페이징 조회")
    @GetMapping("/buyList")
    public String buyListForm(@Parameter(description = "5개씩, 최신순 페이징") @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                              @Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails,
                              @Parameter(description = "결제 내역 정보, 현재 페이지, 시작 페이지, 마지막 페이지를 담을 model") Model model){
        Page<PaymentEntity> list = paymentService.paging(pageable, securityDetails.getUserEntity());

        int nowPage = list.getPageable().getPageNumber() +1 ;
        int startPage = Math.max(nowPage -4, 1);
        int endPage = Math.min(nowPage +5, list.getTotalPages());

        model.addAttribute("paymentList", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "user/payment/buyList";
    }

    @Operation(summary = "home", description = "결제 금액 TOP3 및 main 페이지")
    @GetMapping("/home")
    public String home(@Parameter(description = "결제 금액 TOP3 및 총 결제 금액을 담을 model") Model model,
                       @Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails) {
        List<PaymentDTO> paymentDTOList = paymentService.findTop3(securityDetails.getUserEntity());
        model.addAttribute("top3", paymentDTOList);
        model.addAttribute("sumPrice", paymentService.sumPrice());
        return "user/payment/home";
    }
}
