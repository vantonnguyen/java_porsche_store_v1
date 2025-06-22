package vn.tonnguyen.porsche_store_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.tonnguyen.porsche_store_v1.model.Cart;
import vn.tonnguyen.porsche_store_v1.model.User;
import vn.tonnguyen.porsche_store_v1.repository.CartRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import vn.tonnguyen.porsche_store_v1.service.interf.CartService;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;

import java.security.Principal;

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

    @GetMapping("/list")
    public String showCart(Model model) {
        return "cart/list";
    }

    @PostMapping("/add/{id}")
    public String addToCart(
            Model model,
            @PathVariable("id") Integer carId,
            @RequestParam(value = "quantity",defaultValue = "1") Integer quantity,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        try{
            String username = principal.getName();
            cartService.addToCart(username,carId,quantity);
            return "redirect:/cart/list";
        } catch (Exception e) {
            //model.addAttribute("errorMessage", "Error while adding to cart");
            model.addAttribute("errorMessage", e.getMessage());
            return "/cart/list";
        }
    }

}
