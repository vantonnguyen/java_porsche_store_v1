package vn.tonnguyen.porsche_store_v1.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    @GetMapping()
    public String dashboard() {
        return "admin/dashboard";
    }
}
