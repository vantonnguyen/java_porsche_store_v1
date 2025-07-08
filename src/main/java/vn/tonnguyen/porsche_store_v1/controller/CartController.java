package vn.tonnguyen.porsche_store_v1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import vn.tonnguyen.porsche_store_v1.service.interf.CartService;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final CarService carService;

    @Autowired
    public CartController(CartService cartService, UserService userService, CarService carService) {
        this.cartService = cartService;
        this.userService = userService;
        this.carService = carService;
    }

    @GetMapping()
    public String showCart(
            Model model,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (principal == null) {
            redirectAttributes.addFlashAttribute("inforMessage", "You need to login first");
            return "redirect:/auth/login";
        }
        String username = principal.getName();
        Cart cart = cartService.findByUserId(userService.findIdByUsername(username));
        if (cart == null) {
            redirectAttributes.addFlashAttribute("inforMessage", "Your cart is empty,start shopping now");
            return "cart/list";
        } else {
            model.addAttribute("cartDetails", cartService.findDetails(cart.getId()));
            model.addAttribute("cart", cart);
            return "cart/list";
        }
    }

    @PostMapping("/add/{id}")
    public String addToCart(
            Model model,
            @PathVariable("id") Integer carId,
            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
            Principal principal,
            RedirectAttributes redirectAttributes,
            @RequestHeader(value = "referer", required = false) String referer) {

        if (principal == null) {
            redirectAttributes.addFlashAttribute("inforMessage", "You need to login first");
            return "redirect:/auth/login";
        }
        String username = principal.getName();
        try {
            cartService.addToCart(username, carId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully added to the cart");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return "redirect:" + (referer != null ? referer : "/");
    }

    @PostMapping("/delete/{id}")
    public String deleteFromCart(
            @PathVariable("id") Integer carId,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("infoMessage", "You need to login first");
            return "redirect:/auth/login";
        }
        try {
            String username = principal.getName();
            cartService.deleteFromCart(username, carId);
            redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return "redirect:/cart";
    }

    @PostMapping("/update/{id}")
    public String updateQuantity(
            Model model,
            @PathVariable("id") Integer carId,
            @RequestParam(defaultValue = "0") int delta,
            @AuthenticationPrincipal UserDetails user,
            RedirectAttributes redirectAttributes) {

        try {
            cartService.updateQuantity(user.getUsername(), carId, delta);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/cart";
    }

}
