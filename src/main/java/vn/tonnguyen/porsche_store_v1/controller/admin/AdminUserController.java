package vn.tonnguyen.porsche_store_v1.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.tonnguyen.porsche_store_v1.model.User;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import vn.tonnguyen.porsche_store_v1.service.interf.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    private final  UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showUserList(Model model) {
        model.addAttribute("users",userService.findAll());
        return "admin/users/list";
    }

    @GetMapping("/update/{id}")
    public String updateUser(
            Model model,
            @PathVariable("id") Integer id) {

        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "admin/users/update";
    }

    @PostMapping(value = "/update", consumes = "multipart/form-data")
    public String updateUser(
            Model model,
            @ModelAttribute("user") User updatedUser,
            RedirectAttributes redirectAttributes ) {

        try {
            userService.update(updatedUser);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
            return "redirect:/admin/users/";
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("user",updatedUser);
            return "admin/users/update";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(
            Model model,
            @PathVariable("id") Integer id ) {
        try {
            userService.delete(id);
            model.addAttribute("successMessage", "User deleted successfully");
            return "redirect:/admin/users/";
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "redirect:/admin/users/";
        }
    }



}
