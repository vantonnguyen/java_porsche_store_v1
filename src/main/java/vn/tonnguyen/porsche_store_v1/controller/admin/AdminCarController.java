package vn.tonnguyen.porsche_store_v1.controller.admin;

import jakarta.servlet.annotation.MultipartConfig;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.tonnguyen.porsche_store_v1.extension.UpLoadImage;
import vn.tonnguyen.porsche_store_v1.model.Car;
import vn.tonnguyen.porsche_store_v1.service.interf.CarService;
import vn.tonnguyen.porsche_store_v1.service.interf.CarModelService;

import java.awt.*;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {
    private final CarService carService;
    private final CarModelService carModelService;

    @Autowired
    public AdminCarController(CarService carService, CarModelService carModelService) {
        this.carService = carService;
        this.carModelService = carModelService;
    }

    @GetMapping("/")
    public String showCarList(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "admin/cars/list";
    }

    @GetMapping("/create")
    public String createCar(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("carModels", carModelService.findAll());
        return "admin/cars/create";
    }

    @PostMapping("/create")
    public String createCar(
            Model model,@ModelAttribute("car") Car car,
            @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            if (imageFile.isEmpty() || imageFile == null || !imageFile.getOriginalFilename().endsWith(".png") || !imageFile.getOriginalFilename().endsWith(".png")) {
                model.addAttribute("error", "Please select a file");
                model.addAttribute("carModels", carModelService.findAll());
                return "admin/cars/create";
            }
            String fileName = UpLoadImage.processUpload(imageFile);
            if (fileName == null) {
                model.addAttribute("error", "Upload file failed");
                model.addAttribute("carModels", carModelService.findAll());
                return "admin/cars/create";
            }
            car.setImageUrl(fileName);
            carService.save(car);
            return "redirect:/admin/cars/";
        } catch (Exception ex) {
            model.addAttribute("error", "Đã xảy ra lỗi khi lưu xe: " + ex.getMessage());
            model.addAttribute("car", car);
            model.addAttribute("carModels", carModelService.findAll());
            return "admin/cars/create";
        }
    }

    @RequestMapping("/detail")
    public String showCarDetail(
            Model model,
            @RequestParam int id) {
        model.addAttribute("car",carService.findById(id));
        return "admin/cars/detail";
    }

    @RequestMapping("/delete")
    public String deleteCar(
            Model model,
            @RequestParam int id) {

        model.addAttribute("car",carService.findById(id));
        carService.deleteById(id);
        return "admin/cars/delete";
    }

    @RequestMapping("/update")
    public String updateCar(
            Model model,
            @RequestParam int id) {

        Car car = carService.findById(id);
        model.addAttribute("car", car);
        model.addAttribute("carModels", carModelService.findAll());
        return "admin/cars/update";
    }

    @PostMapping("/update")
    public String updateCar(
            Model model,
            @ModelAttribute("car") Car car,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        try {
            carService.update(car, imageFile);
            redirectAttributes.addFlashAttribute("successMessage", "Car updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Update failed: " + e.getMessage());
            return "redirect:/admin/cars/edit/" + car.getId();
        }
        return "redirect:/admin/cars/";
    }



}
