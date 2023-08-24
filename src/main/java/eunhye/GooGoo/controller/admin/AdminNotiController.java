package eunhye.GooGoo.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;

public class AdminNotiController {

    @GetMapping("/admin/noti")
    public String notiAll() {

        return "admin/noti/noti";
    }

    @GetMapping("/admin/noti/detail")
    public String notiDetail() {

        return "admin/noti/detail";
    }

    @GetMapping("/admin/noti/save")
    public String notiSave() {

        return "admin/noti/save";
    }
}
