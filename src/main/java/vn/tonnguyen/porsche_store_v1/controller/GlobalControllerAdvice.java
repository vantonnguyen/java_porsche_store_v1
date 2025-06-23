package vn.tonnguyen.porsche_store_v1.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.tonnguyen.porsche_store_v1.service.interf.CartDetailService;
import vn.tonnguyen.porsche_store_v1.service.interf.CartService;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;
import org.springframework.ui.Model;
import vn.tonnguyen.porsche_store_v1.model.Cart;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final CartService cartService;
    private final UserService userService;
    private final CartDetailService cartDetailService;

    @Autowired
    public GlobalControllerAdvice(CartService cartService, UserService userService, CartDetailService cartDetailService) {
        this.cartService = cartService;
        this.userService = userService;
        this.cartDetailService = cartDetailService;
    }

    @ModelAttribute
    public void globalAttributes(
            Model model,
            Principal principal) {

        long itemCount = 0;
        if (principal != null) {
            String username = principal.getName();
            Cart cart = cartService.findByUser(userService.findByUsername(username));
            if (cart != null) {
                itemCount = cartDetailService.countByCart(cart);
            }
            model.addAttribute("itemCount", itemCount);
        }
    }
}
