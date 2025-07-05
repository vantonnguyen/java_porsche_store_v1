package vn.tonnguyen.porsche_store_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.tonnguyen.porsche_store_v1.service.interf.CartService;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;
import org.springframework.ui.Model;
import vn.tonnguyen.porsche_store_v1.model.Cart;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public GlobalControllerAdvice(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @ModelAttribute
    public void globalAttributes(
            Model model,
            Principal principal) {

        long itemCount = 0;
        try {
            if (principal != null) {
                String username = principal.getName();
                itemCount = cartService.countItemsByUserId(userService.findByUsername(username).getId());
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("itemCount", itemCount);
    }
}
