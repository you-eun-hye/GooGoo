package eunhye.GooGoo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Operation(summary = "start page", description = "시작 페이지")
    @GetMapping("/")
    public String index(){
        return "/user/index";
    }

}