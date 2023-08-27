package eunhye.GooGoo.controller;

import eunhye.GooGoo.config.security.SecurityDetails;
import eunhye.GooGoo.dto.FlaskDTO;
import eunhye.GooGoo.service.FlaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class FlaskController {

    private final FlaskService flaskService;

    @GetMapping("/googlePassword")
    public String googlePasswordForm(){
        return "user/payment/googlePassword";
    }

    @PostMapping("/googlePassword")
    public String googlePassword(@ModelAttribute FlaskDTO flaskDTO){
        flaskService.save(flaskDTO);
        return "redirect:/buyCheck";
    }

    // Flask 연결
    @GetMapping("/buyCheck")
    public String connectFlask(@AuthenticationPrincipal SecurityDetails securityDetails){
        Long userId = securityDetails.getUserEntity().getId();
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
