package vn.tonnguyen.porsche_store_v1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.tonnguyen.porsche_store_v1.model.User;
import vn.tonnguyen.porsche_store_v1.service.interf.OrderService;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, OrderService orderService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
    }

    @GetMapping("/profile")
    public String showProfile(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            if (user == null) {
                model.addAttribute("errorMessage", "User not found!");
                return "error/error";
            }
            model.addAttribute("user", user);
            return "user/profile";
        } catch (Exception e) {
            log.error("Error while retrieving user", e);
            model.addAttribute("errorMessage", "Error while retrieving user!");
            return "error/error";
        }
    }

    @GetMapping("/update")
    public String showEditForm(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            if (user == null) {
                model.addAttribute("errorMessage", "User not found!");
                return "error/error";
            }
            model.addAttribute("user", user);
            return "user/update";
        } catch (Exception e) {
            log.error("Error while updating user", e);
            model.addAttribute("errorMessage", "Error while updating user!");
            return "error/error";
        }
    }

    @PostMapping("/update")
    public String processEditForm(
            Model model,
            @ModelAttribute("user") User updatedUser,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            if (user == null) {
                model.addAttribute("errorMessage", "User not found!");
                return "error/error";
            }
            user.setEmail(updatedUser.getEmail());
            user.setAddress(updatedUser.getAddress());
            user.setPhone(updatedUser.getPhone());
            user.setFullName(updatedUser.getFullName());
            userService.update(user);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
            return "redirect:/user/profile";
        } catch (Exception e) {
            log.error("Error while updating user", e);
            model.addAttribute("errorMessage", "Error while updating user!");
            return "error/error";
        }
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            if (user == null) {
                model.addAttribute("errorMessage", "User not found!");
                return "error/error";
            }
            model.addAttribute("user", user);
            return "user/change-password";
        } catch (Exception e) {
            log.error("Error while changing password", e);
            model.addAttribute("errorMessage", "Error while changing password!");
            return "error/error";
        }
    }

    @PostMapping("/change-password")
    public String processChangePasswordForm(
            Model model,
            @ModelAttribute("user") User updatedUser,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            if (user == null) {
                model.addAttribute("errorMessage", "User not found!");
                return "error/error";
            }
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            userService.update(user);
            redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully!");
            return "redirect:/user/profile";
        } catch (Exception e) {
            log.error("Error while updating user", e);
            model.addAttribute("errorMessage", "Error while changing password!");
            return "error/error";
        }
    }

    @GetMapping("/orders")
    public String showOrders(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Integer userId = userService.findByUsername(userDetails.getUsername()).getId();
            model.addAttribute("orders", orderService.findByUserId(userId));
            return "user/orders";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error while retrieving orders!");
            return "error/error";
        }
    }

    @GetMapping("orders/{id}")
    public String showDetails(
            Model model,
            @PathVariable("id") Integer id) {
        try {
            model.addAttribute("orderDetail", orderService.findOrderDetailsWithCarByOrderId(id));
            return "user/order-detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error while retrieving order details!");
            return "error/error";
        }
    }

}
