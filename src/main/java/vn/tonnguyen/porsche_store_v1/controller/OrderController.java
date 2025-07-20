package vn.tonnguyen.porsche_store_v1.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.Order;
import vn.tonnguyen.porsche_store_v1.model.User;
import vn.tonnguyen.porsche_store_v1.service.interf.CartService;
import vn.tonnguyen.porsche_store_v1.service.interf.OrderService;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final CartService cartService;
    private final UserService userService;
    private final OrderService orderService;

    public OrderController(CartService cartService, UserService userService, OrderService orderService, PathMatcher pathMatcher) {
        this.cartService = cartService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/create")
    public String showOrder(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = userService.findIdByUsername(userDetails.getUsername());
        Cart cart = cartService.findByUserId(userId);
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("items", cartService.findDetails(cart.getId()));
        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        return "order/create";
    }

    @PostMapping("/create")
    public String createOrder(
            Model model,
            @ModelAttribute User order_user,
            @RequestParam String paymentMethod,
            @AuthenticationPrincipal UserDetails user,
            RedirectAttributes redirectAttributes) {

        try {
            String username = user.getUsername();
            Order order = orderService.createOrder(username,order_user,paymentMethod);
            if(paymentMethod.equals("COD")) {
                redirectAttributes.addFlashAttribute("successMessage", "Order placed successfully (COD).");
                return "redirect:/cart";
            } else {
                return "redirect:/payment/create" + "?amount=" + order.getFinalAmount() + "&orderInfo=" + "Don hang co id la " + order.getId() + "&orderId=" +  + order.getId();
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/cart";
        }
    }
}
