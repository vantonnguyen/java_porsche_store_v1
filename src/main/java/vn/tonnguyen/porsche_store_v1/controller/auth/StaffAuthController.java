package vn.tonnguyen.porsche_store_v1.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff/login")
public class StaffAuthController {
    @GetMapping()
    public String showLoginPage() {
        return "auth/staff/login";
    }


}
