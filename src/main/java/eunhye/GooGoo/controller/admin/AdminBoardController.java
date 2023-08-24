package eunhye.GooGoo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminBoardController {

    @GetMapping("/admin/board")
    public String boardAll() {

        return "admin/board/board";
    }

    @GetMapping("/admin/board/detail")
    public String boardDetail() {

        return "admin/board/detail";
    }
}
