package vn.tonnguyen.porsche_store_v1.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class UserAuthController {
    @GetMapping()
    public String login() {
        return "auth/user/login";
    }
}
