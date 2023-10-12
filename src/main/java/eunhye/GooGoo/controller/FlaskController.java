package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.FlaskDTO;
import eunhye.GooGoo.service.FlaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FlaskController {

    private final FlaskService flaskService;

    @Operation(summary = "googlePasswordForm", description = "구글 비밀번호 입력 페이지")
    @GetMapping("/googlePassword")
    public String googlePasswordForm(){
        return "user/payment/googlePassword";
    }

    @Operation(summary = "googlePassword", description = "구글 비밀번호 임시 저장")
    @ResponseBody
    @PostMapping("/api/googlePassword")
    public ResponseEntity googlePassword(@Parameter(description = "구글 비밀번호") FlaskDTO flaskDTO){
        flaskService.save(flaskDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "buyCheck", description = "결제 내역 전체 조회")
    @GetMapping("/buyCheck")
    public String connectFlask(@Parameter(description = "로그인 중인 계정") @AuthenticationPrincipal SecurityDetails securityDetails){
        UUID userId = securityDetails.getUserEntity().getId();
        String userEmail = securityDetails.getUserEntity().getUserEmail();
        String userPassword = flaskService.getGooglePassword();

        WebClient webClient = WebClient.create("http://127.0.0.1:5000");

        Mono<String> response = webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path("/test")
                        .queryParam("id", userId)
                        .queryParam("email", userEmail)
                        .queryParam("password", userPassword)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class).log();

        response.subscribe(s -> {
            System.out.println("controller" + s);
        });

        flaskService.delete();
        return "user/payment/buyCheck";
    }
}
