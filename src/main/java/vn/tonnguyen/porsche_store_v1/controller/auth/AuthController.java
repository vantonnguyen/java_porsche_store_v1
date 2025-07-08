package vn.tonnguyen.porsche_store_v1.controller.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.tonnguyen.porsche_store_v1.model.User;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;

import java.time.Instant;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @GetMapping("/sign-up")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/sign-up";
    }

    @PostMapping("/sign-up")
    public String processSignUp(
            Model model,
            @ModelAttribute("user") User user,
            RedirectAttributes redirectAttributes) {

        if (user == null) {
            redirectAttributes.addFlashAttribute("inforMessage", "User is null!");
            return "redirect:/auth/sign-up";
        }
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("inforMessage", "Username already exists!");
            model.addAttribute("user", user);
            return "/auth/sign-up";
        }
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("inforMessage", "Email already exists!");
            model.addAttribute("user", user);
            return "/auth/sign-up";
        }
        try {
            user.setRole(User.Role.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus(User.Status.active);
            user.setCreatedAt(Instant.now());
            userService.save(user);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully registered!");
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", user);
            return "/auth/sign-up";
        }
    }

}
