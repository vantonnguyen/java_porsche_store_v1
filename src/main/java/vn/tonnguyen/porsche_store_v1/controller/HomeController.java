package vn.tonnguyen.porsche_store_v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.tonnguyen.porsche_store_v1.repository.CartDetailRepository;
import vn.tonnguyen.porsche_store_v1.service.interf.CartDetailService;
import vn.tonnguyen.porsche_store_v1.service.interf.CartService;
import vn.tonnguyen.porsche_store_v1.service.interf.CategoryService;

@Controller
public class HomeController {
    private final CategoryService categoryService;

    @Autowired
    public HomeController(CategoryService categoryService, CartDetailService cartDetailService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "home/index";
    }
}
